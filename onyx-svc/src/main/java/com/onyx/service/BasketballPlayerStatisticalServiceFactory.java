package com.onyx.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public final class BasketballPlayerStatisticalServiceFactory {
    private final NBAPlayerStatisticsService nbaPlayerStatisticsService;

    public BasketballPlayerStatisticalServiceFactory(NBAPlayerStatisticsService nbaPlayerStatisticsService) {
        this.nbaPlayerStatisticsService = nbaPlayerStatisticsService;
    }
}
