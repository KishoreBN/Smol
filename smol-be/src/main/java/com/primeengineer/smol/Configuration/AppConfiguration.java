package com.primeengineer.smol.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AppConfiguration {

    /**
     * Defines a {@link java.util.concurrent.Executor} bean for handling asynchronous task execution.
     *
     * <p>The executor is configured with:
     * <ul>
     *   <li>Core pool size of 10 threads</li>
     *   <li>Maximum pool size of 20 threads</li>
     *   <li>Queue capacity of 30</li>
     *   <li>Custom thread name prefix: "Smol-threadpool-"</li>
     * </ul>
     * This executor can be used with Spring’s {@code @Async} annotation.</p>
     *
     * @return a configured {@link Executor} instance
     */
    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setMaxPoolSize(20);
        threadPoolTaskExecutor.setQueueCapacity(30);
        threadPoolTaskExecutor.setThreadNamePrefix("Smol-threadpool-");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
