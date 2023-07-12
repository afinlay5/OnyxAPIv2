package com.onyx.service.validation;

import com.onyx.commons.model.BasicBasketballPlayerStatistics;
import com.onyx.commons.model.BasketballPlayerInfo;
import com.onyx.commons.model.BasketballPlayerStatisticsProfile;
import com.onyx.commons.util.Preconditions;

import static com.onyx.commons.util.Preconditions.checkNotBlank;
import static com.onyx.commons.util.Preconditions.checkNotNull;

/* Validates {@link BasketballPlayer */
public class BasketballPlayerStatisticsProfileValidationUtil {
    private static final int BEGINNING_OF_ALL_BASKETBALL = 1891;

    private BasketballPlayerStatisticsProfileValidationUtil() {
    }

    public static BasketballPlayerStatisticsProfile validateBasicBasketballPlayerStaticsProfileRecord(BasketballPlayerStatisticsProfile basketballPlayerStatisticsProfile) {
        checkNotNull(basketballPlayerStatisticsProfile, "Basketball Player Statistics Profile");
        validateBasicPlayerInfo(basketballPlayerStatisticsProfile.basketballPlayerInfo());
        validateBasicBasketballPlayerStatistics(basketballPlayerStatisticsProfile.basicBasketballPlayerStatistics());
        return basketballPlayerStatisticsProfile;
    }

    public static BasketballPlayerInfo validateBasicPlayerInfo(BasketballPlayerInfo basketballPlayerInfo) {
        checkNotNull(basketballPlayerInfo, "Basketball Player Info");
        checkNotBlank(basketballPlayerInfo.firstName(), "BasketballPlayerInfo -> firstName is required but missing or blank");
        checkNotBlank(basketballPlayerInfo.lastName(), "BasketballPlayerInfo -> lastName is required but missing or blank");

        return basketballPlayerInfo;
    }

    public static BasicBasketballPlayerStatistics validateBasicBasketballPlayerStatistics(
            BasicBasketballPlayerStatistics basicBasketballPlayerStatistics) {
        checkNotNull(basicBasketballPlayerStatistics,
                "BasketballPlayerStatisticsProfile -> basicBasketballPlayerStatistics");
        Preconditions.checkIsGreaterThan(basicBasketballPlayerStatistics.season(), BEGINNING_OF_ALL_BASKETBALL,
                String.format("Season must be beyond[%d]", BEGINNING_OF_ALL_BASKETBALL));
        return basicBasketballPlayerStatistics;
    }
}
