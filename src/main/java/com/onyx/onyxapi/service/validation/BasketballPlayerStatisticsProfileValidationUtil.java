package com.onyx.onyxapi.service.validation;

import com.onyx.onyxapi.commons.model.BasicBasketballPlayerStatistics;
import com.onyx.onyxapi.commons.model.BasketballPlayerInfo;
import com.onyx.onyxapi.commons.model.BasketballPlayerStatisticsProfile;

import static com.onyx.onyxapi.commons.util.Preconditions.checkIsGreaterThan;
import static com.onyx.onyxapi.commons.util.Preconditions.checkNotBlank;
import static com.onyx.onyxapi.commons.util.Preconditions.checkNotNull;

/* Validates {@link BasketballPlayer */
public class BasketballPlayerStatisticsProfileValidationUtil {
    private static final int BEGINNING_OF_ALL_BASKETBALL = 1891;

    private BasketballPlayerStatisticsProfileValidationUtil() {
    }

    public static BasketballPlayerStatisticsProfile validateBasicBasketballPlayerStaticsProfileRecord(BasketballPlayerStatisticsProfile basketballPlayerStatisticsProfile) {
        checkNotNull(basketballPlayerStatisticsProfile, "basketballPlayerStatisticsProfile is required and missing");
        validateBasicPlayerInfo(basketballPlayerStatisticsProfile.basketballPlayerInfo());
        validateBasicBasketballPlayerStatistics(basketballPlayerStatisticsProfile.basicBasketballPlayerStatistics());
        return basketballPlayerStatisticsProfile;
    }

    public static BasketballPlayerInfo validateBasicPlayerInfo(BasketballPlayerInfo basketballPlayerInfo) {
        checkNotNull(basketballPlayerInfo, "Basketball Player Info is required and missing");
        checkNotBlank(basketballPlayerInfo.firstName(), "BasketballPlayerInfo -> firstName is required but missing or blank");
        checkNotBlank(basketballPlayerInfo.lastName(), "BasketballPlayerInfo -> lastName is required but missing or blank");

        return basketballPlayerInfo;
    }

    public static BasicBasketballPlayerStatistics validateBasicBasketballPlayerStatistics(
            BasicBasketballPlayerStatistics basicBasketballPlayerStatistics) {
        checkNotNull(basicBasketballPlayerStatistics,
                "BasketballPlayerStatisticsProfile -> basicBasketballPlayerStatistics is required and missing");
        checkIsGreaterThan(basicBasketballPlayerStatistics.getSeason(), BEGINNING_OF_ALL_BASKETBALL,
                String.format("Season must be beyond[%d]", BEGINNING_OF_ALL_BASKETBALL));
        return basicBasketballPlayerStatistics;
    }
}
