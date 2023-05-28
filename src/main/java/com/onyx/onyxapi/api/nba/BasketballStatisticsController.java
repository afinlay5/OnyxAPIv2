package com.onyx.onyxapi.api.nba;

import com.onyx.onyxapi.commons.model.BasketballPlayerInfo;
import com.onyx.onyxapi.commons.model.BasketballPlayerStatisticResponse;
import com.onyx.onyxapi.commons.model.BasketballStatisticsDataSource;
import com.onyx.onyxapi.service.BasketballStatisticalService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

/* RESTful Web API for retrieving Basketball Data*/
@RestController
@RequestMapping("/api/bball")
public final class BasketballStatisticsController {
    private static final String BASKETBALL_LEAGUE_DATA_SOURCE = "BASKETBALL_LEAGUE_DATA_SOURCE";
    private final BasketballStatisticalService basketballStatisticalService;

    public BasketballStatisticsController(BasketballStatisticalService basketballStatisticalService) {
        this.basketballStatisticalService = requireNonNull(basketballStatisticalService,
                "basketballStatisticService is required and missing");
    }

    @GetMapping
    public String foo() {
        return "hello, world!";
    }

    @GetMapping("/nba/player/firstName/{firstName}/lastName/{lastName}/season/{season}")
    public CompletableFuture<BasketballPlayerStatisticResponse> getNBABasicStats(@PathVariable String firstName,
                                                                                 @PathVariable String lastName,
                                                                                 @PathVariable int season,
                                                                                 @RequestHeader(BASKETBALL_LEAGUE_DATA_SOURCE) String basketballLeagueDataSourceHeader) {
        var basketballLeagueDataSource = BasketballStatisticsDataSource.fmtInsensitiveValueOf(basketballLeagueDataSourceHeader);

        return basketballStatisticalService.getNBABasicStats(basketballLeagueDataSource, new BasketballPlayerInfo(firstName, lastName), season);
    }
}
