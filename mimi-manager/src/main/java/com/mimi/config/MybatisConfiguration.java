package com.mimi.config;

import com.mimi.common.superpackage.intercept.MybatisQueryIntercept;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * mybatis配置
 */
@Configuration
public class MybatisConfiguration {
    /**
     * 注册拦截器
     */
    @Bean
    public MybatisQueryIntercept mybatisInterceptor() {
        MybatisQueryIntercept interceptor = new MybatisQueryIntercept();
        Properties properties = new Properties();
        // 可以调用properties.setProperty方法来给拦截器设置一些自定义参数
        interceptor.setProperties(properties);
        return interceptor;
    }
}
