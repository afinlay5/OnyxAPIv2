package com.onyx.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

import static com.onyx.commons.util.Constants.COMMON_THREAD_POOL;

@Configuration
public class OnyxCommonBeans {

    @Bean(COMMON_THREAD_POOL)
    public ExecutorService commonThreadPool() {
        //TODO - Obviously, make and configure our own Executor.
        return ForkJoinPool.commonPool();
    }
}
