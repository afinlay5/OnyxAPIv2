package com.onyx.onyxapi.service;

import com.onyx.onyxapi.commons.model.BasicBasketballStatistics;

import java.util.concurrent.CompletableFuture;

public final class NBABasketballReferenceDataSource {
    public CompletableFuture<BasicBasketballStatistics> getBasicStatistics(String firstName, String lastName, int season) {
        //TODO - @afinlay
        return CompletableFuture.completedFuture(
                BasicBasketballStatistics.builder()
                        .season(season)
                        .ppg(33.3)
                        .rpg(7.9)
                        .apg(8.2)
                        .build());
    }
}
