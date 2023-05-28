package com.onyx.onyxapi.service;

import com.onyx.onyxapi.commons.model.BasicBasketballStatistics;
import com.onyx.onyxapi.commons.model.BasketballStatisticsDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/* Retrieves NBA Player Statistics */

@RequiredArgsConstructor
@Service
public final class NBAPlayerStatisticsService implements BasketballLeaguePlayerStatisticalService {

    private final BasketballLeagueDataSourceFactory basketballLeagueDataSourceFactory;

    @Override
    public CompletableFuture<BasicBasketballStatistics> getBasicPlayerStats(BasketballStatisticsDataSource basketballStatisticsDataSource,
                                                                            String firstName, String lastName, int season) {
       return switch (basketballStatisticsDataSource) {
            case BASKETBALL_REFERENCE -> basketballLeagueDataSourceFactory.getNbaBasketballReferenceDataSource()
                    .getBasicStatistics(firstName, lastName, season);
        };
    }
}
