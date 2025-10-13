package com.example.demo.rmq.consumer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Slf4j
public class AsyncConfig {

    @Bean(name = "asyncTaskExecutor")
    public Executor subscriptionEventProcessorExecutor(
            @Value("${thread.pool.core.size:10}") int corePoolSize,
            @Value("${thread.pool.core.max.size:40}") int maxPoolSize,
            @Value("${thread.pool.core.queue.capacity:100}") int queueCapacity,
            @Value("${thread.pool.core.keep.alive.seconds:60}") int keepAliveSecond) {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("async-");
        executor.setKeepAliveSeconds(keepAliveSecond);
        executor.afterPropertiesSet();
        return executor;
    }
}
