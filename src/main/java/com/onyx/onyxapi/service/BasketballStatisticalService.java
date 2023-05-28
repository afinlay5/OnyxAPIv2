package com.onyx.onyxapi.service;

import com.onyx.onyxapi.commons.BasicBasketballStatistics;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class BasketballStatisticalService {

    //Factory Pattern
    private final BasketballStatisticalServiceFactory basketballStatisticalServiceFactory;

    public BasketballStatisticalService(BasketballStatisticalServiceFactory basketballStatisticalServiceFactory) {
        this.basketballStatisticalServiceFactory = basketballStatisticalServiceFactory;
    }

    public CompletableFuture<BasicBasketballStatistics> getNBABasicStats(String firstName, String surname, int season) {
        //Delegation Pattern
        return basketballStatisticalServiceFactory.getNbaStatisticsService().getBasicStats(firstName, surname, season);
    }

}
