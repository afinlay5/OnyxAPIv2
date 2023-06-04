package com.onyx.onyxapi.service;

import com.onyx.onyxapi.commons.model.BasketballPlayerInfo;
import com.onyx.onyxapi.commons.model.BasketballPlayerStatisticsDataStore;
import com.onyx.onyxapi.commons.model.BasketballPlayerStatisticsProfile;
import com.onyx.onyxapi.commons.model.BasketballStatisticsDataSource;
import com.onyx.onyxapi.service.validation.BasketballPlayerStatisticsProfileValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static com.onyx.onyxapi.commons.util.Preconditions.checkIsPositive;
import static com.onyx.onyxapi.commons.util.Preconditions.checkNotBlank;
import static com.onyx.onyxapi.commons.util.Preconditions.checkNotNull;

@RequiredArgsConstructor
@Slf4j
@Service
/** Service proxy for retrieving and persisting {@link BasketballPlayerStatisticsProfile} */
public final class BasketballPlayerStatisticalService {

    //Factory Pattern
    private final BasketballPlayerStatisticalServiceFactory basketballPlayerStatisticalServiceFactory;

    private final BasketballPlayerStatisticsJDBCDAL basketballPlayerStatisticsJDBCDAL;
    private final BasketballPlayerStatisticsJPADAL basketballPlayerStatisticsJPADAL;

    public CompletableFuture<BasketballPlayerStatisticsProfile> getNBABasicStats(BasketballStatisticsDataSource basketballStatisticsDataSource,
                                                                                 BasketballPlayerInfo basketballPlayerInfo, int season) {
        checkNotNull(basketballStatisticsDataSource, "basketballStatisticsDataSource is required and missing");
        checkNotNull(basketballPlayerInfo, "basketballPlayerInfo is required and missing");
        checkNotBlank(basketballPlayerInfo.firstName(), "Basketball Player Info must include a First name");
        checkNotBlank(basketballPlayerInfo.lastName(), "Basketball Player Info must include a Last name");
        checkIsPositive(season, "season");

        log.info("J1 - #2A) Hit BasketballStatisticalService and successfully validated basketballStatisticsDataSource, basketballPlayerInfo");
        log.info("J1 - #2B) Now we are delegating to the Basketball Statistical Service Factory....");

        //Delegation Pattern
        return basketballPlayerStatisticalServiceFactory
                .getNbaPlayerStatisticsService()
                .getBasicPlayerStats(basketballStatisticsDataSource, basketballPlayerInfo.firstName(), basketballPlayerInfo.lastName(), season)
                .thenApply(basicPlayerStats -> new BasketballPlayerStatisticsProfile(basketballPlayerInfo, basicPlayerStats));
    }

    public CompletableFuture<BasketballPlayerStatisticsProfile> uploadNewBasketballPlayerStats(
            BasketballPlayerStatisticsProfile basketballPlayerStatisticsProfile, String targetDataStoreDestinationHeader) {
        checkNotBlank(targetDataStoreDestinationHeader, "targetDataStoreDestinationHeader is required and missing or blank");
        BasketballPlayerStatisticsProfileValidationUtil.validateBasicBasketballPlayerStaticsProfileRecord(basketballPlayerStatisticsProfile);
        var dataStore = BasketballPlayerStatisticsDataStore.fromFmtAgnosticString(targetDataStoreDestinationHeader,
                "\"Basketball Player Statistics Data Store does not exist");


        if (dataStore == BasketballPlayerStatisticsDataStore.MYDS1) {
            return null;
        } else if (dataStore == BasketballPlayerStatisticsDataStore.MYDS2) {
            return null;
        } else {
            throw new IllegalArgumentException("Basketball Player Statistics Data Store is not supported.");
        }
    }

}
