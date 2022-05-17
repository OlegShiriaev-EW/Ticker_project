package com.example.feign_client.config;

import feign.Logger;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;

@AllArgsConstructor
public class FeignConfig {
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    AuthInterceptor authFeign() {
        return new AuthInterceptor();
    }
}