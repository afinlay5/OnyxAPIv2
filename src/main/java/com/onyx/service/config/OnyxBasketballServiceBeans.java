package com.onyx.service.config;

import com.onyx.commons.config.MetadataCommonBeans;
import com.onyx.service.BasketballLeagueDataProviderFactory;
import com.onyx.service.BasketballPlayerStatisticalService;
import com.onyx.service.BasketballPlayerStatisticalServiceFactory;
import com.onyx.service.NBABasketballReferenceDataProvider;
import com.onyx.service.NBAPlayerStatisticsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.ExecutorService;

@Import({MetadataCommonBeans.class,
        BasketballPlayerStatisticalServiceFactory.class,
        BasketballPlayerStatisticalService.class,
        NBAPlayerStatisticsService.class,
        BasketballLeagueDataProviderFactory.class})
@Configuration
public class OnyxBasketballServiceBeans {

    @Bean
    public NBABasketballReferenceDataProvider nbaBasketballReferenceDataSource(ExecutorService executorService) {
        return new NBABasketballReferenceDataProvider(executorService);
    }
}
