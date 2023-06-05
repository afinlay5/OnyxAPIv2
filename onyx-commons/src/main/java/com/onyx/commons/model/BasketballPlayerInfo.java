package com.onyx.commons.model;

public record BasketballPlayerInfo(String firstName, String lastName) {
    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }
}
