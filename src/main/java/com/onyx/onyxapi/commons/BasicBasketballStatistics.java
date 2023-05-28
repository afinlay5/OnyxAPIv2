package com.onyx.onyxapi.commons;

import lombok.ToString;

@ToString
public record BasicBasketballStatistics(double ppg, double rpg, double apg) {
    public String toPrettyString() {
        return String.format("Basic Stats are: PPG[%s], RPG[%s], APG[%s]", ppg, rpg, apg);
    }
}
