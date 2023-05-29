package com.onyx.onyxapi.commons.model;

import lombok.Builder;
import lombok.Value;


@Builder
@Value
public class BasicBasketballStatistics {
    //NOTE TO SELF - Might be more efficient for OSs to handle float vs double

    private final int season;
    private final float ppg;
    private final float rpg;
    private final float apg;

}
