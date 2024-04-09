package com.mimi.config;

import com.mimi.interceptor.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

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
