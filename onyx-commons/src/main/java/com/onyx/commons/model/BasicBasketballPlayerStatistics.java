package com.onyx.commons.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@JsonDeserialize(builder = BasicBasketballPlayerStatistics.BasicBasketballPlayerStatisticsBuilder.class)
public class BasicBasketballPlayerStatistics {
    //NOTE TO SELF - Might be more efficient for OSs to handle float vs double

    private final int season;
    private final float ppg;
    private final float rpg;
    private final float apg;

}
