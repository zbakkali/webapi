package com.example.webapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import java.util.concurrent.TimeUnit;

import static org.springframework.http.CacheControl.maxAge;

@Configuration
public class CacheWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebContentInterceptor interceptor = new WebContentInterceptor();
        interceptor.addCacheMapping(
                maxAge(60, TimeUnit.SECONDS)
                        .noTransform()
                        .mustRevalidate(),
                "/*", "/contacts*", "/skills*");
        registry.addInterceptor(interceptor);
    }
}
