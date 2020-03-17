package vc.thinker.config;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import vc.thinker.common.dozer.MyDozerBeanMapperFactoryBean;

import java.io.IOException;

/**
 * Bean映射 配置
 *
 * @author HeTongHao
 * @since 2019-08-16 16:28
 */
@Configuration
public class DozerBeanConfig {

    @Bean(name = "dozerBean")
    public MyDozerBeanMapperFactoryBean configDozer() throws IOException {
        MyDozerBeanMapperFactoryBean mapper = new MyDozerBeanMapperFactoryBean();
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = pathMatchingResourcePatternResolver.getResources("classpath*:/dozer/*Mapping.xml");
        Resource[] jdk8resources = pathMatchingResourcePatternResolver.getResources("classpath*:dozerJdk8Converters.xml");
        resources = ArrayUtils.addAll(resources, jdk8resources);
        mapper.setMappingFiles(resources);
        return mapper;
    }
}
