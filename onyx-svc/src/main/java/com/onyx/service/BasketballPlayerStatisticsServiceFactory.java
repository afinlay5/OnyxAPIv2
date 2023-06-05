package com.onyx.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public final class BasketballPlayerStatisticsServiceFactory {
    private final NBAPlayerStatisticsService nbaPlayerStatisticsService;

    public BasketballPlayerStatisticsServiceFactory(NBAPlayerStatisticsService nbaPlayerStatisticsService) {
        this.nbaPlayerStatisticsService = nbaPlayerStatisticsService;
    }
}
