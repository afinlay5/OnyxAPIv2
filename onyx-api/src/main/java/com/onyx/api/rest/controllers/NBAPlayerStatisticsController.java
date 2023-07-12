package com.onyx.api.rest.controllers;

import com.onyx.commons.model.BasketballPlayerInfo;
import com.onyx.commons.model.BasketballPlayerStatisticsProfile;
import com.onyx.commons.model.BasketballStatisticsDataSource;
import com.onyx.service.BasketballPlayerStatisticsService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.input.CloseShieldInputStream;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Duration;
import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.CompletableFuture;

import static com.onyx.api.util.Constants.HTTP_HEADER_MULTIPART_CONTENT_TYPE;
import static com.onyx.commons.util.Constants.TARGET_DATA_STORE_DESTINATION;
import static com.onyx.commons.util.Preconditions.requireNotNull;

/* RESTful Web API for retrieving NBA Basketball Data*/
@RestController
@RequestMapping("/api/nba")
@Slf4j
public final class NBAPlayerStatisticsController {
    private static final String NBA_DATA_SOURCE = "BASKETBALL_LEAGUE_DATA_SOURCE";
    private static final int MULTIPART_FILE_BUFFER_SIZE = 8192;
    private static final long BULK_UPLOAD_TIMEOUT_MILLISECONDS = Duration.ofMinutes(5).toMillis();
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

    private static ByteArrayOutputStream bufferedReadMultipartFileStreamIntoByteArrayOutputStream(MultipartFile multipartFile) {
        try (var multipartFileStream = CloseShieldInputStream.wrap(multipartFile.getInputStream())) {
            var byteArrayOutputStream = new ByteArrayOutputStream(MULTIPART_FILE_BUFFER_SIZE);
            byteArrayOutputStream.write(multipartFileStream);
            return byteArrayOutputStream;
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    //    @TODO("Unused targetDataStoreDestination")
//    @TODO("Use Headers")
//    @TODO("thenApply could cause NPE")
    @PostMapping
    public CompletableFuture<BasketballPlayerStatisticsProfile> create(
            @RequestBody BasketballPlayerStatisticsProfile newBasketballPlayerStatisticsProfile,
            @RequestHeader(TARGET_DATA_STORE_DESTINATION) String targetDataStoreDestination) {
        return basketballPlayerStatisticsService.uploadNewBasketballPlayerStats(newBasketballPlayerStatisticsProfile)
                .whenComplete((ignored, throwable) -> {
                    if (throwable != null) {
                        log.error("POST /api/nba/create - Oops! We had an Exception[{}]", throwable.getMessage());
                    } else {
                        log.info("POST /api/nba/create - Success!");
                    }
                });
    }

    @PostMapping(value = "/upload", headers = HTTP_HEADER_MULTIPART_CONTENT_TYPE)
    public DeferredResult<SortedSet<BasketballPlayerStatisticsProfile>> upload(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestHeader Map<String, String> headers) {

        val deferredResult = new DeferredResult<SortedSet<BasketballPlayerStatisticsProfile>>(BULK_UPLOAD_TIMEOUT_MILLISECONDS);

        try (var multipartFileByteStream = bufferedReadMultipartFileStreamIntoByteArrayOutputStream(multipartFile)) {
            basketballPlayerStatisticsService.uploadStatisticsProfiles(multipartFileByteStream)
                    .thenApply(deferredResult::setResult)
                    .whenComplete((ignored, throwable) -> {
                        if (throwable != null) {
                            log.error("POST /api/nba/upload - Oops! We had an Exception[{}]", throwable.getMessage());
                        } else {
                            log.info("POST /api/nba/upload - Success!");
                        }
                    });
            return deferredResult;
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

}
