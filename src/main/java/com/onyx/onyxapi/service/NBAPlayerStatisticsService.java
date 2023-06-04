package com.onyx.onyxapi.service;

import com.onyx.onyxapi.commons.model.BasicBasketballPlayerStatistics;
import com.onyx.onyxapi.commons.model.BasketballStatisticsDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.CompletableFuture;

import static com.onyx.onyxapi.commons.util.Preconditions.checkArgIsWithinClosedRange;
import static com.onyx.onyxapi.commons.util.Preconditions.requireNotBlank;
import static java.util.Objects.requireNonNull;

/* Retrieves NBA Player Statistics */

@RequiredArgsConstructor
@Slf4j
@Service
public final class NBAPlayerStatisticsService implements BasketballLeaguePlayerStatisticalService {

    private static final short NBA_MINIMUM_SEASON_YEAR = 1946;
    /* NOTE: We are approximating, this is merely an estimation */
    private static final LocalDate NBA_START_OF_SEASON = LocalDate.of(LocalDate.now().getYear(), Month.OCTOBER, 1);
    private static final short NBA_MAXIMUM_SEASON_YEAR = determineMaximumSeasonYear();

    private final BasketballLeagueDataProviderFactory basketballLeagueDataProviderFactory;

    private static short determineMaximumSeasonYear() {
        val currentDate = LocalDate.now();
        val currentYear = currentDate.getYear();
        return currentDate.isBefore(NBA_START_OF_SEASON) ? (short) (currentYear - 1) : (short) (currentYear);
    }

    @Override
    public CompletableFuture<BasicBasketballPlayerStatistics> getBasicPlayerStats(BasketballStatisticsDataSource basketballStatisticsDataSource,
                                                                                  String firstName, String lastName, int season) {
        log.info("J1 - #3A) We were delegated to NBA Player Statistic Service based on our context");

        requireNonNull(basketballStatisticsDataSource, "basketballStatisticsDataSource is required and missing");
        requireNotBlank(firstName, "firstName is required and missing");
        requireNotBlank(lastName, "lastName is required and missing");
        checkArgIsWithinClosedRange(season, NBA_MINIMUM_SEASON_YEAR, NBA_MAXIMUM_SEASON_YEAR, "Season");

        log.info("J1 - #3B) We passed validation inside our NBA Player Statistic Service");

        return switch (basketballStatisticsDataSource) {
            case BASKETBALL_REFERENCE -> basketballLeagueDataProviderFactory.getNbaBasketballReferenceDataProvider()
                    .getBasicStatistics(firstName, lastName, season);
        };
    }
}
