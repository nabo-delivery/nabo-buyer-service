package com.nabo.buyer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@ConfigurationProperties(prefix="nabo")
@Getter
@Setter
public class AppConfig {
    private String jwtSecret;
    private long jwtTokenDaysToExpire;
}
