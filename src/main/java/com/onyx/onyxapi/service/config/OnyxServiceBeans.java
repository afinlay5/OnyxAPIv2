package com.onyx.onyxapi.service.config;

import com.onyx.onyxapi.service.NBAStatisticsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OnyxServiceBeans {

    @Bean
    public NBAStatisticsService nbaStatService() {
        return new NBAStatisticsService();
    }
}
