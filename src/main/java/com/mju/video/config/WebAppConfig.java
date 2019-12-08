package com.mju.video.config;

import com.mju.video.interceptor.LoginInterceptor;
import com.mju.video.interceptor.privilegeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/loginpage","/login","/forget","/logout","/register","/css/**","/js/**","/images/**");
        registry.addInterceptor(getPrivilegeInterceptor()).addPathPatterns("/");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:/D:/image/");
    }

    @Bean
    public privilegeInterceptor getPrivilegeInterceptor(){
        return new privilegeInterceptor();
    }
}
