package com.petcove.inventoryservice.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*")); // 允许所有来源
        config.setAllowedMethods(List.of("*")); // 允许所有方法
        config.setAllowedHeaders(List.of("*")); // 允许所有请求头
        config.setAllowCredentials(false); // 是否允许携带认证信息（true/false）

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config); // 适用于 /api/ 开头的路径

        return new CorsWebFilter(source);
    }
}