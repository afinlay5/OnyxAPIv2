package com.onyx.onyxapi.service;

import com.onyx.onyxapi.commons.model.BasketballPlayerInfo;
import com.onyx.onyxapi.commons.model.BasketballPlayerStatisticResponse;
import com.onyx.onyxapi.commons.model.BasketballStatisticsDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static com.onyx.onyxapi.commons.util.Preconditions.requireGreaterThan;
import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Service
public final class BasketballStatisticalService {

    private static final short MINIMUM_SEASON_YEAR = 1960;

    //Factory Pattern
    private final BasketballStatisticalServiceFactory basketballStatisticalServiceFactory;

    public CompletableFuture<BasketballPlayerStatisticResponse> getNBABasicStats(BasketballStatisticsDataSource basketballStatisticsDataSource,
                                                                        BasketballPlayerInfo basketballPlayerInfo, int season) {
        requireNonNull(basketballStatisticsDataSource, "basketballStatisticsDataSource is required and missing");
        requireNonNull(basketballPlayerInfo, "basketballPlayerInfo is required and missing");
        requireGreaterThan(season, MINIMUM_SEASON_YEAR, String.format("Season must be later than %s", MINIMUM_SEASON_YEAR));

        //TODO - Season requireWithinRange

        //Delegation Pattern
        return basketballStatisticalServiceFactory
                .getNbaPlayerStatisticsService()
                .getBasicPlayerStats(basketballStatisticsDataSource, basketballPlayerInfo.firstName(), basketballPlayerInfo.lastName(), season)
                .thenApply(basicPlayerStats -> new BasketballPlayerStatisticResponse(basketballPlayerInfo, basicPlayerStats));
    }

}
