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

    /**
     * 无需令牌的可以访问的URL
     */
    private final List<String> whiteURL = new ArrayList<>();

}
