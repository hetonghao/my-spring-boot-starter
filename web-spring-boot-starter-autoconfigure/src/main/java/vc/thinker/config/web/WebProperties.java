package vc.thinker.config.web;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author HeTongHao
 * @since 2020/3/16 23:39
 */
@ConfigurationProperties(prefix = "web")
public class WebProperties {
    private String test1;
    private String test2;

    public String getTest1() {
        return test1;
    }

    public void setTest1(String test1) {
        this.test1 = test1;
    }

    public String getTest2() {
        return test2;
    }

    public void setTest2(String test2) {
        this.test2 = test2;
    }
}
