package com.onyx.service;

import com.onyx.commons.model.BasketballPlayerInfo;
import com.onyx.commons.model.BasketballPlayerStatisticsProfile;
import com.onyx.commons.model.BasketballStatisticsDataSource;
import com.onyx.commons.util.Preconditions;
import com.onyx.dal.BasketballPlayerStatisticsDALFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static com.onyx.commons.util.Preconditions.checkNotBlank;
import static com.onyx.service.validation.BasketballPlayerStatisticsProfileValidationUtil.validateBasicBasketballPlayerStaticsProfileRecord;

@RequiredArgsConstructor
@Service
@Slf4j
/** Retrieves and persists {@link BasketballPlayerStatisticsProfile} */
public final class BasketballPlayerStatisticsService {

    //Factory Pattern
    private final BasketballPlayerStatisticsServiceFactory basketballPlayerStatisticsServiceFactory;

    private final BasketballPlayerStatisticsDALFactory basketballPlayerStatisticsDALFactory;


    public CompletableFuture<BasketballPlayerStatisticsProfile> getNBABasicStats(BasketballStatisticsDataSource basketballStatisticsDataSource,
                                                                                 BasketballPlayerInfo basketballPlayerInfo, int season) {
        Preconditions.checkNotNull(basketballStatisticsDataSource, "basketballStatisticsDataSource is required and missing");
        Preconditions.checkNotNull(basketballPlayerInfo, "basketballPlayerInfo is required and missing");
        checkNotBlank(basketballPlayerInfo.firstName(), "Basketball Player Info must include a First name");
        checkNotBlank(basketballPlayerInfo.lastName(), "Basketball Player Info must include a Last name");
        Preconditions.checkIsPositive(season, "season");

        log.info("J1 - #2A) Hit BasketballStatisticsService and successfully validated basketballStatisticsDataSource, basketballPlayerInfo");
        log.info("J1 - #2B) Now we are delegating to the Basketball Statistics Service Factory....");

        //Delegation Pattern
        return basketballPlayerStatisticsServiceFactory
                .getNbaPlayerStatisticsService()
                .getBasicPlayerStats(basketballStatisticsDataSource, basketballPlayerInfo.firstName(), basketballPlayerInfo.lastName(), season)
                .thenApply(basicPlayerStats -> new BasketballPlayerStatisticsProfile(basketballPlayerInfo, basicPlayerStats));
    }

    public CompletableFuture<BasketballPlayerStatisticsProfile> uploadNewBasketballPlayerStats(
            BasketballPlayerStatisticsProfile basketballPlayerStatisticsProfile) {
        validateBasicBasketballPlayerStaticsProfileRecord(basketballPlayerStatisticsProfile);

        return basketballPlayerStatisticsDALFactory.getNbaPlayerStatisticsDAL()
                .persistBasketballPlayerStats(basketballPlayerStatisticsProfile);
    }

}