package com.onyx.commons.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.onyx.commons.util.Preconditions;
import lombok.val;

public enum BasketballStatisticsDataSource {
    BASKETBALL_REFERENCE;

    @JsonCreator
    public static BasketballPlayerStatisticsDataStore fromFmtAgnosticString(String basketballLeagueDataSource) {
        Preconditions.checkNotBlank(basketballLeagueDataSource, "basketballLeagueDataSource is required and missing");

        BasketballPlayerStatisticsDataStore targetConstant = null;
        for (val constant : BasketballPlayerStatisticsDataStore.values()) {
            if (constant.name().contentEquals(basketballLeagueDataSource.strip())) {
                targetConstant = constant;
                break;
            }
        }
        if (targetConstant == null) {
            throw new IllegalArgumentException("Basketball Player Statistics Data Source does not exist");
        } else {
            return targetConstant;
        }
    }
}
