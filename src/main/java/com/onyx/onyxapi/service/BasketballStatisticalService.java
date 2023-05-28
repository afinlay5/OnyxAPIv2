package com.onyx.onyxapi.service;

import com.onyx.onyxapi.commons.model.BasketballPlayerInfo;
import com.onyx.onyxapi.commons.model.BasketballPlayerStatisticResponse;
import com.onyx.onyxapi.commons.model.BasketballStatisticsDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public final class BasketballStatisticalService {

    //Factory Pattern
    private final BasketballStatisticalServiceFactory basketballStatisticalServiceFactory;

    public CompletableFuture<BasketballPlayerStatisticResponse> getNBABasicStats(BasketballStatisticsDataSource basketballStatisticsDataSource,
                                                                        BasketballPlayerInfo basketballPlayerInfo, int season) {
        //Delegation Pattern
        return basketballStatisticalServiceFactory
                .getNbaPlayerStatisticsService()
                .getBasicPlayerStats(basketballStatisticsDataSource, basketballPlayerInfo.firstName(), basketballPlayerInfo.lastName(), season)
                .thenApply(basicPlayerStats -> new BasketballPlayerStatisticResponse(basketballPlayerInfo, basicPlayerStats));
    }

}
