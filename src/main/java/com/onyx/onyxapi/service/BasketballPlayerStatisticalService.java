package com.onyx.onyxapi.service;

import com.onyx.onyxapi.commons.model.BasketballPlayerInfo;
import com.onyx.onyxapi.commons.model.BasketballPlayerStatisticsProfile;
import com.onyx.onyxapi.commons.model.BasketballStatisticsDataSource;
import com.onyx.onyxapi.service.validation.BasketballPlayerStatisticsProfileValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static com.onyx.onyxapi.commons.util.Preconditions.checkArgIsPositive;
import static com.onyx.onyxapi.commons.util.Preconditions.checkNonNull;

@RequiredArgsConstructor
@Slf4j
@Service
/** Service proxy for retrieving and persisting {@link BasketballPlayerStatisticsProfile} */
public final class BasketballPlayerStatisticalService {

    //Factory Pattern
    private final BasketballPlayerStatisticalServiceFactory basketballPlayerStatisticalServiceFactory;

    public CompletableFuture<BasketballPlayerStatisticsProfile> getNBABasicStats(BasketballStatisticsDataSource basketballStatisticsDataSource,
                                                                                 BasketballPlayerInfo basketballPlayerInfo, int season) {
        checkNonNull(basketballStatisticsDataSource, "basketballStatisticsDataSource is required and missing");
        checkNonNull(basketballPlayerInfo, "basketballPlayerInfo is required and missing");
        checkArgIsPositive(season, "season");

        log.info("J1 - #2A) Hit BasketballStatisticalService and successfully validated basketballStatisticsDataSource, basketballPlayerInfo");
        log.info("J1 - #2B) Now we are delegating to the Basketball Statistical Service Factory....");

        //Delegation Pattern
        return basketballPlayerStatisticalServiceFactory
                .getNbaPlayerStatisticsService()
                .getBasicPlayerStats(basketballStatisticsDataSource, basketballPlayerInfo.firstName(), basketballPlayerInfo.lastName(), season)
                .thenApply(basicPlayerStats -> new BasketballPlayerStatisticsProfile(basketballPlayerInfo, basicPlayerStats));
    }

    public CompletableFuture<BasketballPlayerStatisticsProfile> uploadNewBasketballPlayerStats(
            BasketballPlayerStatisticsProfile basketballPlayerStatisticsProfile) {
        BasketballPlayerStatisticsProfileValidationUtil.validateBasicRecord(basketballPlayerStatisticsProfile));
    }

}
