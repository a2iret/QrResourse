package com.kg.config;

import com.kg.intercaptor.HmacInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class HmacInterceptorConfig implements WebMvcConfigurer {
    private final HmacInterceptor hmacInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(hmacInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
