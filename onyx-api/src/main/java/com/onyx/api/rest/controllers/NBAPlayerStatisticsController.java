package com.onyx.api.rest.controllers;

import com.onyx.commons.model.BasketballPlayerInfo;
import com.onyx.commons.model.BasketballPlayerStatisticsProfile;
import com.onyx.commons.model.BasketballStatisticsDataSource;
import com.onyx.service.BasketballPlayerStatisticsService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.CompletableFuture;

import static com.onyx.api.util.Constants.HTTP_HEADER_MULTIPART_CONTENT_TYPE;
import static com.onyx.commons.util.Constants.TARGET_DATA_STORE_DESTINATION;
import static com.onyx.commons.util.Preconditions.requireNotNull;

//TODO - Swagger

/* RESTful Web API for retrieving NBA Basketball Data*/
@RestController
@RequestMapping("/api/nba")
@Slf4j
public final class NBAPlayerStatisticsController {
    private static final String NBA_DATA_SOURCE = "BASKETBALL_LEAGUE_DATA_SOURCE";
    private static final long BULK_UPLOAD_TIMEOUT_MILLISECONDS = 5 * 1000L;
    private final BasketballPlayerStatisticsService basketballPlayerStatisticsService;

    public NBAPlayerStatisticsController(BasketballPlayerStatisticsService basketballPlayerStatisticsService) {
        this.basketballPlayerStatisticsService = requireNotNull(basketballPlayerStatisticsService,
                "basketballStatisticService");
    }

    @GetMapping
    public String foo() {
        log.info("We called foo.");
        return "hello, world!";
    }

    @GetMapping("/player/firstName/{firstName}/lastName/{lastName}/season/{season}")
    public CompletableFuture<BasketballPlayerStatisticsProfile> getNBABasicStats(@PathVariable String firstName,
                                                                                 @PathVariable String lastName,
                                                                                 @PathVariable int season,
                                                                                 @RequestHeader(NBA_DATA_SOURCE) BasketballStatisticsDataSource basketballLeagueDataSourceHeader) {
        log.info("J1 - #1) Hit endpoint /api/bball//nba/player/firstName/{firstName}/lastName/{lastName}/season/{season}");

        return basketballPlayerStatisticsService
                .getNBABasicStats(basketballLeagueDataSourceHeader, new BasketballPlayerInfo(firstName, lastName, null), season)
                .whenComplete((ignored, throwable) -> {
                    if (throwable != null) {
                        log.error("J1 - #5) Oops! We had an Exception[{}]", throwable.getMessage());
                    } else {
                        log.info("J1 - #5) Successfully handled request for endpoint /api/bball//nba/player/firstName/{firstName}/lastName/{lastName}/season/{season}\")");
                    }
                });
    }

    @PostMapping
    public CompletableFuture<BasketballPlayerStatisticsProfile> upload(
            @RequestBody BasketballPlayerStatisticsProfile newBasketballPlayerStatisticsProfile,
            @RequestHeader(TARGET_DATA_STORE_DESTINATION) String targetDataStoreDestination) {
        return basketballPlayerStatisticsService.uploadNewBasketballPlayerStats(newBasketballPlayerStatisticsProfile)
                .whenComplete((ignored, throwable) -> {
                    if (throwable != null) {
                        log.error("POST /api/nba - Oops! We had an Exception[{}]", throwable.getMessage());
                    } else {
                        log.info("POST /api/nba - Success!");
                    }
                });
    }

    @PostMapping(value = "/upload", headers = HTTP_HEADER_MULTIPART_CONTENT_TYPE)
    public DeferredResult<SortedSet<BasketballPlayerStatisticsProfile>> upload(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestHeader Map<String, String> headers) {

        val deferredResult = new DeferredResult<SortedSet<BasketballPlayerStatisticsProfile>>(BULK_UPLOAD_TIMEOUT_MILLISECONDS);

        basketballPlayerStatisticsService.uploadStatisticsProfiles(null)
                .thenApply(response ->
                        deferredResult.setResult(response)
                )
                .whenComplete((ignored, throwable) -> {
                    if (throwable != null) {
                        log.error("POST /api/nba - Oops! We had an Exception[{}]", throwable.getMessage());
                    } else {
                        log.info("POST /api/nba - Success!");
                    }
                });
        return deferredResult;
    }

}
