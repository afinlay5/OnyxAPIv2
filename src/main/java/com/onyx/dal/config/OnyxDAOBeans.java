package com.onyx.dal.config;

import com.onyx.dal.NBAPlayerStatisticsDAL;
import com.onyx.dal.WNBAPlayerStatisticsDAL;
import com.onyx.dal.impl.NBAPlayerStatisticsJPADALImpl;
import com.onyx.dal.impl.WNBAPlayerStatisticsDALImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OnyxDAOBeans {
    @Bean
    public NBAPlayerStatisticsDAL nbaPlayerStatisticsDS1DALImpl() {
        return new NBAPlayerStatisticsJPADALImpl();
    }

    @Bean
    public NBAPlayerStatisticsDAL nbaPlayerStatisticsDS2DALImpl() {
        return new NBAPlayerStatisticsJPADALImpl();
    }

    @Bean
    public WNBAPlayerStatisticsDAL wnbaPlayerStatisticsDALImpl1() {
        return new WNBAPlayerStatisticsDALImpl();
    }
}
