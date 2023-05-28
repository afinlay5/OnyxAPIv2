package com.onyx.onyxapi.service;

import com.onyx.onyxapi.commons.BasicBasketballStatistics;

import java.util.concurrent.CompletableFuture;

public class NBAStatisticsService implements BasketballLeagueStatisticalService {

    @Override
    public CompletableFuture<BasicBasketballStatistics> getBasicStats(String firstName, String lastName, int season) {
        return CompletableFuture.completedFuture(new BasicBasketballStatistics(22.1, 6.1, 5.3));
    }
}
