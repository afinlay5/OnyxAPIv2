package com.onyx.onyxapi.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

@Configuration
public class MetadataCommonBeans {
    @Bean
    public ExecutorService commonThreadPool() {
        //TODO - Obviously, make and configure our own Executor.
        return ForkJoinPool.commonPool();
    }
}
