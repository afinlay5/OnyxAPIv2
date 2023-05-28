package com.onyx.onyxapi.commons.model;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public final class BasicBasketballStatistics {
    private final int season;
    private final float ppg;
    private final float rpg;
    private final float apg;

    public static BasicBasketballStatistics badInstanceForSeason(int season) {
        //TODO - require within range ?
//        requireNonNull(season, "season is required and missing");

        return new BasicBasketballStatisticsBuilder().season(season).build();
    }

    //NOTE TO SELF - Might be more efficient for OSs to handle float vs double
}
