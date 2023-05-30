package com.onyx.onyxapi.service.config;

import com.onyx.onyxapi.commons.config.MetadataCommonBeans;
import com.onyx.onyxapi.service.BasketballLeagueDataProviderFactory;
import com.onyx.onyxapi.service.BasketballStatisticalService;
import com.onyx.onyxapi.service.BasketballStatisticalServiceFactory;
import com.onyx.onyxapi.service.NBABasketballReferenceDataProvider;
import com.onyx.onyxapi.service.NBAPlayerStatisticsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.ExecutorService;

@Import({MetadataCommonBeans.class,
        BasketballStatisticalServiceFactory.class,
        BasketballStatisticalService.class,
        NBAPlayerStatisticsService.class,
        BasketballLeagueDataProviderFactory.class})
@Configuration
public class OnyxBasketballServiceBeans {

    @Bean
    public NBABasketballReferenceDataProvider nbaBasketballReferenceDataSource(ExecutorService executorService) {
        return new NBABasketballReferenceDataProvider(executorService);
    }
}
