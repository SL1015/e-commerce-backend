package com.petcove.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;

import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;

import org.springframework.security.web.server.SecurityWebFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SpringSecurityConfig {
    //private final String[] freeResourceUrls = {"/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/aggregate/**"};
    @Bean
    public SecurityWebFilterChain SpringSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.csrf()
                .disable()
                .authorizeExchange(exchange -> exchange
                        //.pathMatchers("/eureka/**").permitAll() // Eureka 允许访问
                        .pathMatchers("/swagger-ui.html", "/webjars/swagger-ui/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/aggregate/**", "/eureka/**").permitAll() // 允许 OpenAPI 访问
                        .anyExchange().authenticated()) // 其他路径需要认证
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
        return serverHttpSecurity.build();
    }

    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*")); // 允许所有来源
        config.setAllowedMethods(Arrays.asList("GET", "POST")); // 允许的方法
        config.setAllowedHeaders(List.of("*")); // 允许所有请求头
        //config.setAllowCredentials(true); // 允许携带认证信息（JWT Cookie等）
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 适用于所有路径

        return new CorsWebFilter(source);
    }

}
