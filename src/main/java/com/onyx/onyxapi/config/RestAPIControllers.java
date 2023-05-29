package com.onyx.onyxapi.config;

import com.onyx.onyxapi.api.nba.BasketballStatisticsController;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Import(BasketballStatisticsController.class)
@Configuration
public class RestAPIControllers {
}
