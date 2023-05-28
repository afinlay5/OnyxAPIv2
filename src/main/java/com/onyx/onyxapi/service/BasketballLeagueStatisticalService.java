package com.onyx.onyxapi.service;

import com.onyx.onyxapi.commons.BasicBasketballStatistics;

import java.util.concurrent.CompletableFuture;

public interface BasketballLeagueStatisticalService {
    CompletableFuture<BasicBasketballStatistics> getBasicStats(String firstName, String lastName, int season);
}
