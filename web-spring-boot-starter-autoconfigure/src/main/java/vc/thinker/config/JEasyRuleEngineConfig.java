package vc.thinker.config;

import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * J-Easy 规则引擎配置
 *
 * @author HeTongHao
 * @since 2019-08-13 21:08
 */
@Configuration
public class JEasyRuleEngineConfig {

    @Bean
    public RulesEngine rulesEngine() {
        return new DefaultRulesEngine();
    }
}
