package com.onyx.onyxapi.service;

import com.onyx.onyxapi.commons.BasicBasketballStatistics;
import com.onyx.onyxapi.commons.BasketballStatisticsDataSource;

import java.util.concurrent.CompletableFuture;

/* Operations for Basketball League Statistical Service */
public interface BasketballLeaguePlayerStatisticalService {

    /**
     * Retrieves Basic Players Statistics from this League
     * @param basketballStatisticsDataSource League Data Source
     * @param firstName Player First Name
     * @param lastName Player Last name
     * @param season Season expressed as a year
     */
    CompletableFuture<BasicBasketballStatistics> getBasicPlayerStats(BasketballStatisticsDataSource basketballStatisticsDataSource,
                                                                     String firstName, String lastName, int season);
}
