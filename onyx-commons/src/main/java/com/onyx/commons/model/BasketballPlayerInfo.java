package com.onyx.commons.model;

//@Lombok.ToString -- Not supported for anything other than class/enum
//@Lombok. EqualsAndHashCode -- only supported for a class

//@JsonDeserialize(builder = BasicBasketballPlayerStatistics.BasicBasketballPlayerStatisticsBuilder.class)
public record BasketballPlayerInfo(String firstName, String lastName) {
    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }
}
