package com.onyx.onyxapi.commons.model;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public final class BasicBasketballStatistics {
    private final int season;
    private final double ppg;
    private final double rpg;
    private final double apg;
}
