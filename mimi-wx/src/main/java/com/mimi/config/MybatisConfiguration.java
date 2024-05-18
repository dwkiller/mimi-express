package com.mimi.config;

import com.mimi.core.common.superpackage.intercept.MybatisQueryIntercept;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MybatisConfiguration {

    @Bean
    public MybatisQueryIntercept mybatisInterceptor() {
        MybatisQueryIntercept interceptor = new MybatisQueryIntercept();
        Properties properties = new Properties();
        // 可以调用properties.setProperty方法来给拦截器设置一些自定义参数
        interceptor.setProperties(properties);
        return interceptor;
    }
}
