package com.onyx.api.controllers.nba;

import com.onyx.service.BasketballPlayerStatisticalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNull;

//TODO - Swagger

/* RESTful Web API for retrieving WNBA Player Data*/
@RestController
@RequestMapping("/api/wnba")
@Slf4j
public final class WNBAPlayerStatisticsController {
    private static final String WNBA_DATA_SOURCE = "BASKETBALL_LEAGUE_DATA_SOURCE";
    private final BasketballPlayerStatisticalService basketballPlayerStatisticalService;

    public WNBAPlayerStatisticsController(BasketballPlayerStatisticalService basketballPlayerStatisticalService) {
        this.basketballPlayerStatisticalService = requireNonNull(basketballPlayerStatisticalService,
                "basketballStatisticService is required and missing");
    }

    @GetMapping
    public String foo() {
        log.info("We called foo.");
        return "hello, world!";
    }
}