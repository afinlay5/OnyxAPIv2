package com.onyx.onyxapi.api.nba;

import com.onyx.onyxapi.commons.BasicBasketballStatistics;
import com.onyx.onyxapi.commons.BasketballStatisticsDataSource;
import com.onyx.onyxapi.service.BasketballStatisticalService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.google.common.base.Preconditions.checkArgument;
import static com.onyx.onyxapi.commons.Preconditions.requireNotBlank;
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
    public CompletableFuture<BasicBasketballStatistics> getNBABasicStats(@PathVariable String firstName,
                                                                         @PathVariable String lastName,
                                                                         @PathVariable int season,
                                                                         @RequestHeader Map<String, String> headers) {
        var basketballLeagueDataSourceHeader = requireNotBlank(headers.get(BASKETBALL_LEAGUE_DATA_SOURCE),
                String.format("%s is required and missing", BASKETBALL_LEAGUE_DATA_SOURCE));

        var basketballLeagueDataSource = BasketballStatisticsDataSource.fmtInsensitiveValueOf(basketballLeagueDataSourceHeader);

        return basketballStatisticalService.getNBABasicStats(basketballLeagueDataSource, firstName, lastName, season);
    }
}
