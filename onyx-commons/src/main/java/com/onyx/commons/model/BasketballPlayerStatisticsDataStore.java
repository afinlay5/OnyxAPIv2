package com.onyx.commons.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.val;

import static com.onyx.commons.util.Preconditions.checkNotBlank;

public enum BasketballPlayerStatisticsDataStore {
    MYDS1,
    MYDS2;

    @JsonCreator
    public static BasketballPlayerStatisticsDataStore fromFmtAgnosticString(String basketballPlayerStatisticsDataStore) {
        var targetConstant = fromFmtAgnosticString(basketballPlayerStatisticsDataStore);
        if (targetConstant == null) {
            throw new IllegalArgumentException("Basketball Player Statistics Data Store does not exist");
        } else {
            return targetConstant;
        }
    }

    public static BasketballPlayerStatisticsDataStore getFromFmtAgnosticString(String basketballPlayerStatisticsDataStore) {
        checkNotBlank(basketballPlayerStatisticsDataStore, "basketballPlayerStatisticsDataStore is required and missing");

        BasketballPlayerStatisticsDataStore targetConstant = null;
        for (val constant : BasketballPlayerStatisticsDataStore.values()) {
            if (constant.name().contentEquals(basketballPlayerStatisticsDataStore.strip())) {
                targetConstant = constant;
                break;
            }
        }
        return targetConstant;
    }
}
