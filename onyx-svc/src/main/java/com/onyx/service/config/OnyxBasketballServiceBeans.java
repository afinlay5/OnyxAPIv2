package com.onyx.service.config;

import com.onyx.commons.config.OnyxCommonBeans;
import com.onyx.dal.BasketballPlayerStatisticsDALFactory;
import com.onyx.service.BasketballLeagueDataProviderFactory;
import com.onyx.service.BasketballPlayerStatisticsService;
import com.onyx.service.BasketballPlayerStatisticsServiceFactory;
import com.onyx.service.NBABasketballReferenceDataProvider;
import com.onyx.service.NBAPlayerStatisticsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.ExecutorService;

@Import({OnyxCommonBeans.class,
        BasketballPlayerStatisticsServiceFactory.class,
        BasketballPlayerStatisticsDALFactory.class,
        BasketballPlayerStatisticsService.class,
        NBAPlayerStatisticsService.class,
        BasketballLeagueDataProviderFactory.class})
@Configuration
public class OnyxBasketballServiceBeans {

    @Bean
    public NBABasketballReferenceDataProvider nbaBasketballReferenceDataSource(ExecutorService executorService) {
        return new NBABasketballReferenceDataProvider(executorService);
    }
}
