package com.onyx.api.controllers.nba;

import com.onyx.commons.model.BasketballPlayerInfo;
import com.onyx.commons.model.BasketballPlayerStatisticsProfile;
import com.onyx.commons.model.BasketballStatisticsDataSource;
import com.onyx.service.BasketballPlayerStatisticalService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.SortedSet;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

//TODO - Swagger

/* RESTful Web API for retrieving Basketball Data*/
@RestController
@RequestMapping("/api/nba")
@Slf4j
public final class NBAPlayerStatisticsController {
    private static final String NBA_DATA_SOURCE = "BASKETBALL_LEAGUE_DATA_SOURCE";
    private static final long BULK_UPLOAD_TIMEOUT_MILLISECONDS = 5 * 1000L;
    private final BasketballPlayerStatisticalService basketballPlayerStatisticalService;

    public NBAPlayerStatisticsController(BasketballPlayerStatisticalService basketballPlayerStatisticalService) {
        this.basketballPlayerStatisticalService = requireNonNull(basketballPlayerStatisticalService,
                "basketballStatisticService is required and missing");
    }

    @GetMapping
    public String foo() {
        log.info("We called foo.");
        return "hello, world!";
    }

    //TODO: You could refactor this so there is a dedicated NBA controller. Fine for now.
    @GetMapping("/nba/player/firstName/{firstName}/lastName/{lastName}/season/{season}")
    public CompletableFuture<BasketballPlayerStatisticsProfile> getNBABasicStats(@PathVariable String firstName,
                                                                                 @PathVariable String lastName,
                                                                                 @PathVariable int season,
                                                                                 @RequestHeader(NBA_DATA_SOURCE) String basketballLeagueDataSourceHeader) {
        log.info("J1 - #1) Hit endpoint /api/bball//nba/player/firstName/{firstName}/lastName/{lastName}/season/{season}");
        val basketballLeagueDataSource = BasketballStatisticsDataSource.fmtInsensitiveValueOf(basketballLeagueDataSourceHeader);
        return basketballPlayerStatisticalService
                .getNBABasicStats(basketballLeagueDataSource, new BasketballPlayerInfo(firstName, lastName), season)
                .whenComplete((ignored, throwable) -> {
                    if (throwable != null) {
                        log.error("J1 - #5) Oops! We had an Exception[{}]", throwable.getMessage());
                    } else {
                        log.info("J1 - #5) Successfully handled request for endpoint /api/bball//nba/player/firstName/{firstName}/lastName/{lastName}/season/{season}\")");
                    }
                });
    }

    @PostMapping
    public BasketballPlayerStatisticsProfile uploadNewBasketballPlayerStatisticsProfile(
            @RequestBody BasketballPlayerStatisticsProfile newBasketballPlayerStatisticsProfile) {
        return null;
    }

    @PostMapping
    public DeferredResult<SortedSet<BasketballPlayerStatisticsProfile>> bulkUploadNewBasketballPlayerStatisticsProfile() {
        var deferredResult = new DeferredResult<SortedSet<BasketballPlayerStatisticsProfile>>(BULK_UPLOAD_TIMEOUT_MILLISECONDS);

        return deferredResult;
    }

}
