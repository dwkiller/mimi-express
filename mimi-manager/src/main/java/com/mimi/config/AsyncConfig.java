package com.mimi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Value("${robot-grab.in.executor.corePoolSize:10}")
    private int corePoolSize;

    @Value("${robot-grab.in.executor.maxPoolSize:50}")
    private int maxPoolSize;

    @Value("${robot-grab.in.executor.queueCapacity:200}")
    private int queueCapacity;

    @Value("${robot-grab.in.executor.keepAliveSeconds:100}")
    private int keepAliveSeconds;

    @Bean(name = "robotGrabTaskExecutor")
    public Executor robotGrabTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix("robot-in-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
