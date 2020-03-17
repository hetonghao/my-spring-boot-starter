package vc.thinker.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import vc.thinker.common.cache.SpringContextHolder;

/**
 * Akka 配置
 *
 * @author HeTongHao
 * @since 2019-08-16 16:26
 */
@Configuration
public class AkkaConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    @Lazy(false)
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    /**
     * Akka配置
     *
     * @return
     */
    @Bean
    public Config akkaConfiguration() {
        return ConfigFactory.load();
    }
//
//    /**
//     * spring 集成Akka
//     *
//     * @return
//     */
//    @Bean
//    public SpringExtension springExtension() {
//        return new SpringExtension();
//    }
//
//    /**
//     * Actor system singleton for this application.
//     */
//    @Bean
//    public ActorSystem actorSystem() {
//        ActorSystem system = ActorSystem.create("AkkaTaskProcessing", akkaConfiguration());
//        springExtension().initialize(applicationContext);
//        return system;
//    }
}
