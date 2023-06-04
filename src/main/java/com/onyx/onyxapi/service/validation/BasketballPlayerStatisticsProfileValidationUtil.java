package com.onyx.onyxapi.service.validation;

import com.onyx.onyxapi.commons.model.BasketballPlayerStatisticsProfile;

import static com.onyx.onyxapi.commons.util.Preconditions.checkNonNull;

/* Validates {@link BasketballPlayer */
public class BasketballPlayerStatisticsProfileValidationUtil {
    private BasketballPlayerStatisticsProfileValidationUtil() {
    }

    public static BasketballPlayerStatisticsProfile validateBasicRecord(BasketballPlayerStatisticsProfile basketballPlayerStatisticsProfile) {
        checkNonNull(basketballPlayerStatisticsProfile, "basketballPlayerStatisticsProfile is required and missing");

        return basketballPlayerStatisticsProfile;
    }
}
