package com.onyx.onyxapi.service;

import com.onyx.onyxapi.commons.BasicBasketballStatistics;

import java.util.concurrent.CompletableFuture;

public final class NBABasketballReferenceDataSource {
    public CompletableFuture<BasicBasketballStatistics> getBasicStatistics(String firstName, String lastName, int season) {
        return CompletableFuture.completedFuture(new BasicBasketballStatistics(21.2, 11.2, 3.1));
    }
}
