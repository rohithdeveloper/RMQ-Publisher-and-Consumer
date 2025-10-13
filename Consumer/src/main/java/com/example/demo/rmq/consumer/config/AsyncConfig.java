package com.example.demo.rmq.consumer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
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
        executor.setThreadNamePrefix("rabbit-consumer-");
        executor.setKeepAliveSeconds(keepAliveSecond);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        
        log.info("✅ RabbitMQ ThreadPoolExecutor initialized: core={}, max={}, queue={}, prefix=rabbit-consumer-", 
                 corePoolSize, maxPoolSize, queueCapacity);
        
        return executor;
    }
}
