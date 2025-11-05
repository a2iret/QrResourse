package com.kg.config;

import com.kg.intercaptor.HmacInterceptor;
import com.kg.intercaptor.LoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final HmacInterceptor hmacInterceptor;
    private final LoggingInterceptor loggingInterceptorConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(hmacInterceptor);
        registry.addInterceptor(loggingInterceptorConfig);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
