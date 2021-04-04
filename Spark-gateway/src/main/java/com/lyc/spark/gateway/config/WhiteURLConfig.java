package com.lyc.spark.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 *  无需令牌的可以访问的URL---
 */

@Data
@RefreshScope
@Component
@ConfigurationProperties("spark.project.whiteurl")
public class WhiteURLConfig {


    private static final List<String> defaultWhiteUrl = new ArrayList<>();

    static {
        defaultWhiteUrl.add("/example");
        // 获取令牌接口放行
        defaultWhiteUrl.add("/token/**");
        // 获取验证码接口放行
        defaultWhiteUrl.add("/captcha/**");
    }


    /**
     * 无需令牌的可以访问的URL 可以在配置文件中动态配置
     */
    private final List<String> whiteURL = new ArrayList<>();

}
