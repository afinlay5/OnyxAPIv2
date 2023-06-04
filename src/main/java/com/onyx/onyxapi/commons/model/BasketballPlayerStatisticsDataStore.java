package com.onyx.onyxapi.commons.model;

import lombok.val;

import static com.onyx.onyxapi.commons.util.Preconditions.checkNotBlank;

public enum BasketballPlayerStatisticsDataStore {
    MYDS1,
    MYDS2;

    public static BasketballPlayerStatisticsDataStore fromFmtAgnosticString(String targetDataStoreDestinationHeader, String errorMsg) {
        checkNotBlank(targetDataStoreDestinationHeader, "targetDataStoreDestinationHeader is required and missing");

        BasketballPlayerStatisticsDataStore targetConstant = null;
        for (val constant : BasketballPlayerStatisticsDataStore.values()) {
            if (constant.name().contentEquals(targetDataStoreDestinationHeader.strip())) {
                targetConstant = constant;
                break;
            }
        }
        if (targetConstant == null) {
            throw new IllegalArgumentException(errorMsg);
        } else {
            return targetConstant;
        }
    }
}
