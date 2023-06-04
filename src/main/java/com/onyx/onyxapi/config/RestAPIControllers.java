package com.onyx.onyxapi.config;

import com.onyx.onyxapi.api.nba.BasketballPlayerStatisticsController;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Import(BasketballPlayerStatisticsController.class)
@Configuration
public class RestAPIControllers {
}
