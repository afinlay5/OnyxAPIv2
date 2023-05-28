package com.onyx.onyxapi.service;

import com.onyx.onyxapi.commons.BasicBasketballStatistics;
import com.onyx.onyxapi.commons.BasketballStatisticsDataSource;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public final class BasketballStatisticalService {

    //Factory Pattern
    private final BasketballStatisticalServiceFactory basketballStatisticalServiceFactory;

    public BasketballStatisticalService(BasketballStatisticalServiceFactory basketballStatisticalServiceFactory) {
        this.basketballStatisticalServiceFactory = basketballStatisticalServiceFactory;
    }

    public CompletableFuture<BasicBasketballStatistics> getNBABasicStats(BasketballStatisticsDataSource basketballStatisticsDataSource,
                                                                         String firstName, String surname, int season) {
        //Delegation Pattern
        return basketballStatisticalServiceFactory
                .getNbaPlayerStatisticsService()
                .getBasicPlayerStats(basketballStatisticsDataSource, firstName, surname, season);
    }

}
