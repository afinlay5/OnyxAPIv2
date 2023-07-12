package com.onyx.api.rest.controllers;

import com.onyx.service.BasketballPlayerStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.onyx.commons.util.Preconditions.requireNotNull;

//TODO - Swagger

/* RESTful Web API for retrieving WNBA Player Data*/
@RestController
@RequestMapping("/api/wnba")
@Slf4j
public final class WNBAPlayerStatisticsController {
    private static final String WNBA_DATA_SOURCE = "BASKETBALL_LEAGUE_DATA_SOURCE";
    private final BasketballPlayerStatisticsService basketballPlayerStatisticsService;

    public WNBAPlayerStatisticsController(BasketballPlayerStatisticsService basketballPlayerStatisticsService) {
        this.basketballPlayerStatisticsService = requireNotNull(basketballPlayerStatisticsService,
                "basketballStatisticService");
    }

    @GetMapping
    public String foo() {
        log.info("We called foo.");
        return "hello, world!";
    }
}