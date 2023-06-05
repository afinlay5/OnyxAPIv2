package com.onyx.service;

import com.onyx.commons.model.BasicBasketballPlayerStatistics;
import com.onyx.commons.model.BasketballStatisticsDataSource;

import java.util.concurrent.CompletableFuture;

/* Operations for Basketball League Statistics Service */
public interface BasketballLeaguePlayerStatisticsService {

    /**
     * Retrieves Basic Players Statistics from this League
     *
     * @param basketballStatisticsDataSource League Data Source
     * @param firstName                      Player First Name
     * @param lastName                       Player Last name
     * @param season                         Season expressed as a year
     */
    CompletableFuture<BasicBasketballPlayerStatistics> getBasicPlayerStats(BasketballStatisticsDataSource basketballStatisticsDataSource,
                                                                           String firstName, String lastName, int season);
}
