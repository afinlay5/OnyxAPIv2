package com.onyx.onyxapi.api.nba;

import com.onyx.onyxapi.commons.model.BasketballPlayerInfo;
import com.onyx.onyxapi.commons.model.BasketballPlayerStatisticResponse;
import com.onyx.onyxapi.commons.model.BasketballStatisticsDataSource;
import com.onyx.onyxapi.service.BasketballStatisticalService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

/* RESTful Web API for retrieving Basketball Data*/
@RestController
@RequestMapping("/api/bball")
@Slf4j
public final class BasketballStatisticsController {
    private static final String BASKETBALL_LEAGUE_DATA_SOURCE = "BASKETBALL_LEAGUE_DATA_SOURCE";
    private final BasketballStatisticalService basketballStatisticalService;

    public BasketballStatisticsController(BasketballStatisticalService basketballStatisticalService) {
        this.basketballStatisticalService = requireNonNull(basketballStatisticalService,
                "basketballStatisticService is required and missing");
    }

    @GetMapping
    public String foo() {
        log.info("We called foo.");
        return "hello, world!";
    }

    @GetMapping("/nba/player/firstName/{firstName}/lastName/{lastName}/season/{season}")
    public CompletableFuture<BasketballPlayerStatisticResponse> getNBABasicStats(@PathVariable String firstName,
                                                                                 @PathVariable String lastName,
                                                                                 @PathVariable int season,
                                                                                 @RequestHeader(BASKETBALL_LEAGUE_DATA_SOURCE) String basketballLeagueDataSourceHeader) {
        log.info("J1 - #1) Hit endpoint /api/bball//nba/player/firstName/{firstName}/lastName/{lastName}/season/{season}");
        val basketballLeagueDataSource = BasketballStatisticsDataSource.fmtInsensitiveValueOf(basketballLeagueDataSourceHeader);
        return basketballStatisticalService
                .getNBABasicStats(basketballLeagueDataSource, new BasketballPlayerInfo(firstName, lastName), season)
                .whenComplete((ignored, throwable) -> {
                    if (throwable != null) {
                        log.error("J1 - #5) Oops! We had an Exception[{}]", throwable.getMessage());
                    } else {
                        log.info("J1 - #5) Successfully handled request for endpoint /api/bball//nba/player/firstName/{firstName}/lastName/{lastName}/season/{season}\")");
                    }
                });
    }
}
