package com.onyx.api.config;

import com.onyx.api.controllers.nba.NBAPlayerStatisticsController;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Import(NBAPlayerStatisticsController.class)
@Configuration
public class RestAPIControllers {
}
