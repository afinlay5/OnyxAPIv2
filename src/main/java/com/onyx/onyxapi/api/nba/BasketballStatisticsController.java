package com.onyx.onyxapi.api.nba;

import com.onyx.onyxapi.commons.BasicBasketballStatistics;
import com.onyx.onyxapi.service.BasketballStatisticalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

/* RESTful Web API for retrieving Basketball Data*/
@RestController("/bball")
public class BasketballStatisticsController {
    private final BasketballStatisticalService basketballStatisticalService;

    public BasketballStatisticsController(BasketballStatisticalService basketballStatisticalService) {
        this.basketballStatisticalService = requireNonNull(basketballStatisticalService,
                "basketballStatisticService is required and missing");
    }

    @GetMapping("nba/player/firstName/{firstName}/lastName/{lastName}/season/{season}")
    public CompletableFuture<BasicBasketballStatistics> getNBABasicStats(@PathVariable String firstName,
                                                                         @PathVariable String lastName,
                                                                         @PathVariable int season) {
        return basketballStatisticalService.getNBABasicStats(firstName, lastName, season);
    }
}
