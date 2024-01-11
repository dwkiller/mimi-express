package com.mimi.wx.config;

import com.mimi.wx.interceptor.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@EnableWebMvc
@Configuration
public class WebInterceptorConfig extends WebMvcConfigurationSupport{
	
	@Bean
    UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(userInterceptor()).addPathPatterns("/security/**");
	}
}
