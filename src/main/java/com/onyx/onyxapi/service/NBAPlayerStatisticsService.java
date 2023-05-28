package com.onyx.onyxapi.service;

import com.onyx.onyxapi.commons.BasicBasketballStatistics;
import com.onyx.onyxapi.commons.BasketballStatisticsDataSource;

import java.util.concurrent.CompletableFuture;

/* Retrieves NBA Player Statistics */
public final class NBAPlayerStatisticsService implements BasketballLeaguePlayerStatisticalService {

    BasketballLeagueDataSourceFactory basketballLeagueDataSourceFactory;

    @Override
    public CompletableFuture<BasicBasketballStatistics> getBasicPlayerStats(BasketballStatisticsDataSource basketballStatisticsDataSource,
                                                                            String firstName, String lastName, int season) {
       return switch (basketballStatisticsDataSource) {
            case BASKETBALL_REFERENCE -> basketballLeagueDataSourceFactory.nbaBasketballReferenceDataSource
                    .getBasicStatistics(firstName, lastName, season);
        };
    }
}
