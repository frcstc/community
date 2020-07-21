package com.hzgood.community.config;

import com.hzgood.community.interceptor.JWTAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public JWTAuthInterceptor jwtAuthInterceptor() {
        return new JWTAuthInterceptor();
    }
}
