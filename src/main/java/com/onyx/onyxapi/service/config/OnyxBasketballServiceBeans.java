package com.onyx.onyxapi.service.config;

import com.onyx.onyxapi.service.NBABasketballReferenceDataSource;
import com.onyx.onyxapi.service.NBAPlayerStatisticsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OnyxBasketballServiceBeans {
    @Bean
    public NBAPlayerStatisticsService nbaStatisticsService() {
        return new NBAPlayerStatisticsService();
    }

    @Bean
    public NBABasketballReferenceDataSource nbaBasketballReferenceDataSource() {
        return  new NBABasketballReferenceDataSource();
    }
}
