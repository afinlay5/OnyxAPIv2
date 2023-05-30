package com.onyx.onyxapi.service;

import com.onyx.onyxapi.commons.model.BasketballPlayerInfo;
import com.onyx.onyxapi.commons.model.BasketballPlayerStatisticResponse;
import com.onyx.onyxapi.commons.model.BasketballStatisticsDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static com.onyx.onyxapi.commons.util.Preconditions.checkArgIsPositive;
import static com.onyx.onyxapi.commons.util.Preconditions.checkNonNull;

@RequiredArgsConstructor
@Slf4j
@Service
public final class BasketballStatisticalService {

    //Factory Pattern
    private final BasketballStatisticalServiceFactory basketballStatisticalServiceFactory;

    public CompletableFuture<BasketballPlayerStatisticResponse> getNBABasicStats(BasketballStatisticsDataSource basketballStatisticsDataSource,
                                                                                 BasketballPlayerInfo basketballPlayerInfo, int season) {
        checkNonNull(basketballStatisticsDataSource, "basketballStatisticsDataSource is required and missing");
        checkNonNull(basketballPlayerInfo, "basketballPlayerInfo is required and missing");
        checkArgIsPositive(season, "season");

        log.info("J1 - #2A) Hit BasketballStatisticalService and successfully validated basketballStatisticsDataSource, basketballPlayerInfo");
        log.info("J1 - #2B) Now we are delegating to the Basketball Statistical Service Factory....");

        //Delegation Pattern
        return basketballStatisticalServiceFactory
                .getNbaPlayerStatisticsService()
                .getBasicPlayerStats(basketballStatisticsDataSource, basketballPlayerInfo.firstName(), basketballPlayerInfo.lastName(), season)
                .thenApply(basicPlayerStats -> new BasketballPlayerStatisticResponse(basketballPlayerInfo, basicPlayerStats));
    }

}
