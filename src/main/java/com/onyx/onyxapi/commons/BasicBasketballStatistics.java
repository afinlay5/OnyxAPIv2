package com.onyx.onyxapi.commons;

//@Lombok.ToString -- Not supported for anything other than class/enum
public record BasicBasketballStatistics(double ppg, double rpg, double apg) {

    @Override
    public String toString() {
        return String.format("Basic Stats are: PPG[%s], RPG[%s], APG[%s]", ppg, rpg, apg);
    }
}
