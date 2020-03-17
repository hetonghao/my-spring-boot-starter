package vc.thinker.config.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import vc.thinker.common.web.LocalDateTimeDeserializer;
import vc.thinker.common.web.LocalDateTimeSerializer;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * WebMvc配置
 *
 * @author HeTongHao
 * @since 2019-07-11 17:00
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(WebProperties.class)
public class WebAutoConfiguration implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        List<HttpMessageConverter<?>> myConverters = new ArrayList<>();
        // 添加 json 解析
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        jacksonConverter.setPrettyPrint(true);

        jacksonConverter.setObjectMapper(new ObjectMapper()
                //支持jdk8的json转换(如转换LocalDateTime)
                .registerModules(new JavaTimeModule()
                        .addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE)
                        .addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE)
                )
                //值为null的json字段忽略
                .setSerializationInclusion(JsonInclude.Include.NON_NULL));

        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("utf-8"));

        /**
         * 初步估计升级到 spring boot 2.0造成converters顺序有问题，会优使用框架定义的 converter,
         * 这里调整自定义的在前
         */
        myConverters.add(stringHttpMessageConverter);
        myConverters.add(jacksonConverter);
        myConverters.addAll(converters);
        converters.clear();
        converters.addAll(myConverters);
    }

    @Bean
    @Primary
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder
                .serializerByType(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE)
                .deserializerByType(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
    }
}
