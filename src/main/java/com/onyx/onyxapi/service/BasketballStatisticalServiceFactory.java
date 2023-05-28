package com.onyx.onyxapi.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class BasketballStatisticalServiceFactory {
    NBAStatisticsService nbaStatisticsService;
}
