package com.example.management.config;

import com.example.management.handler.LoginInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web配置
 * @author 14158
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
    @Resource
    LoginInterceptor loginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截test接口，后续实际遇到需要拦截的接口时，在配置拦截接口
        registry.addInterceptor(loginInterceptor);
//                .addPathPatterns("/comments/create/change")
//                .addPathPatterns("/articles/publish")
//                .addPathPatterns("/articles/setFavorites")
//                .addPathPatterns("/articles/favorites");

    }
}
