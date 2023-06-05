package com.onyx.commons.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.onyx.commons.model.marshalling.BasketballStatisticsDataSourceDeserializer;
import com.onyx.commons.util.Preconditions;
import lombok.val;

@JsonDeserialize(using = BasketballStatisticsDataSourceDeserializer.class)
public enum BasketballStatisticsDataSource {
    BASKETBALL_REFERENCE;

    @JsonCreator
    public static BasketballStatisticsDataSource fromFmtAgnosticString(String basketballLeagueDataSource) {
        Preconditions.checkNotBlank(basketballLeagueDataSource, "basketballLeagueDataSource is required and missing");

        BasketballStatisticsDataSource targetConstant = null;
        for (val constant : BasketballStatisticsDataSource.values()) {
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
