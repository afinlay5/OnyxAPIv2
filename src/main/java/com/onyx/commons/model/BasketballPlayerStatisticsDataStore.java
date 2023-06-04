package com.onyx.commons.model;

import com.onyx.commons.util.Preconditions;
import lombok.val;

public enum BasketballPlayerStatisticsDataStore {
    MYDS1,
    MYDS2;

    public static BasketballPlayerStatisticsDataStore fromFmtAgnosticString(String targetDataStoreDestinationHeader, String errorMsg) {
        Preconditions.checkNotBlank(targetDataStoreDestinationHeader, "targetDataStoreDestinationHeader is required and missing");

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
