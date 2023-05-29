package com.onyx.onyxapi.service;

import com.onyx.onyxapi.commons.model.BasketballPlayerInfo;
import com.onyx.onyxapi.commons.model.BasketballPlayerStatisticResponse;
import com.onyx.onyxapi.commons.model.BasketballStatisticsDataSource;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.CompletableFuture;

import static com.onyx.onyxapi.commons.util.Preconditions.checkArgIsWithinClosedRange;
import static com.onyx.onyxapi.commons.util.Preconditions.checkNonNull;

@RequiredArgsConstructor
@Service
public final class BasketballStatisticalService {

    private static final short NBA_MINIMUM_SEASON_YEAR = 1946;
    /* NOTE: We are approximating, this is merely an estimation */
    private static final LocalDate NBA_START_OF_SEASON = LocalDate.of(LocalDate.now().getYear(), Month.OCTOBER, 1);
    private static final short NBA_MAXIMUM_SEASON_YEAR = determineMaximumSeasonYear();


    //Factory Pattern
    private final BasketballStatisticalServiceFactory basketballStatisticalServiceFactory;


    private static short determineMaximumSeasonYear() {
        val currentDate = LocalDate.now();
        val currentYear = currentDate.getYear();
        return currentDate.isBefore(NBA_START_OF_SEASON) ? (short) (currentYear - 1) : (short) (currentYear);
    }

    public CompletableFuture<BasketballPlayerStatisticResponse> getNBABasicStats(BasketballStatisticsDataSource basketballStatisticsDataSource,
                                                                                 BasketballPlayerInfo basketballPlayerInfo, int season) {
        checkNonNull(basketballStatisticsDataSource, "basketballStatisticsDataSource is required and missing");
        checkNonNull(basketballPlayerInfo, "basketballPlayerInfo is required and missing");
        checkArgIsWithinClosedRange(season, NBA_MINIMUM_SEASON_YEAR, NBA_MAXIMUM_SEASON_YEAR, "Season");

        //Delegation Pattern
        return basketballStatisticalServiceFactory
                .getNbaPlayerStatisticsService()
                .getBasicPlayerStats(basketballStatisticsDataSource, basketballPlayerInfo.firstName(), basketballPlayerInfo.lastName(), season)
                .thenApply(basicPlayerStats -> new BasketballPlayerStatisticResponse(basketballPlayerInfo, basicPlayerStats));
    }

}
