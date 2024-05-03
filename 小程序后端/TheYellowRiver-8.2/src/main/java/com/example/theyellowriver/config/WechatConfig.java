package com.example.theyellowriver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author 14158
 */
@Configuration
public class WechatConfig {

    @Value("${wechat.appid}")
    private String appId;

    @Value("${wechat.secret}")
    private String secret;

    public String getAppId() {
        return appId;
    }

    public String getSecret() {
        return secret;
    }
}