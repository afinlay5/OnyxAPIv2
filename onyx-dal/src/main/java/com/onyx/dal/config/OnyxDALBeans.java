package com.onyx.dal.config;

import com.onyx.dal.NBAPlayerStatisticsDAL;
import com.onyx.dal.WNBAPlayerStatisticsDAL;
import com.onyx.dal.impl.NBAPlayerStatisticsJDBCDALImpl;
import com.onyx.dal.impl.NBAPlayerStatisticsJPADALImpl;
import com.onyx.dal.impl.WNBAPlayerStatisticsDALImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableJpaRepositories(
//        basePackageClasses = OnyxJpaRepo.class,
//        includeFilters = @ComponentScan.Filter(classes = OnyxJpaRepo.class)
//)
//@EntityScan(basePackageClasses = OnyxJpaDAOEntity.class)
@Configuration
public class OnyxDALBeans {
    @Bean
    public NBAPlayerStatisticsDAL nbaPlayerStatisticsJPADALImpl() {
        return new NBAPlayerStatisticsJPADALImpl();
    }

    @Bean
    public NBAPlayerStatisticsDAL nbaPlayerStatisticsJDBCDALImpl() {
        return new NBAPlayerStatisticsJDBCDALImpl();
    }

    @Bean
    public WNBAPlayerStatisticsDAL wnbaPlayerStatisticsDALImpl1() {
        return new WNBAPlayerStatisticsDALImpl();
    }
}
