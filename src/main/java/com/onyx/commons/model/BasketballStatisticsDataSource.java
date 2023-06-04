package com.onyx.commons.model;

import java.util.Locale;

import static com.onyx.commons.util.Preconditions.checkNotBlank;

public enum BasketballStatisticsDataSource {
    BASKETBALL_REFERENCE;

    public static BasketballStatisticsDataSource fmtInsensitiveValueOf(String basketballLeagueDataSource) {
        checkNotBlank(basketballLeagueDataSource, "basketballLeagueDataSource is required and missing");

        return valueOf(basketballLeagueDataSource.strip().toUpperCase(Locale.ROOT));
    }
}
