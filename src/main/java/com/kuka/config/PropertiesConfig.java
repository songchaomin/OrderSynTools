package com.kuka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="reg")
public class PropertiesConfig {
    public static  String sn;

    public String getSn() {
        return sn;
    }
@Value("${reg.sn}")
    public void setSn(String sn) {
        this.sn = sn;
    }
}
