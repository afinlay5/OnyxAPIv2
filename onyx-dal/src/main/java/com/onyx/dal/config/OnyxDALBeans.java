package com.onyx.dal.config;

import com.onyx.commons.annotation.TODO;
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
    public static final String NBA_STAT_JPA_DAL_IMPL = "NBA_STAT_JPA_DAL_IMPL";
    public static final String NBA_STAT_JDBC_DAL_IMPL = "NBA_STAT_JDBC_DAL_IMPL";
    public static final String WNBA_STAT_JPA_DAL_IMPL = "WNBA_STAT_JPA_DAL_IMPL";

    @Bean(NBA_STAT_JPA_DAL_IMPL)
    public NBAPlayerStatisticsDAL nbaPlayerStatisticsJPADALImpl() {
        return new NBAPlayerStatisticsJPADALImpl();
    }

    @Bean(NBA_STAT_JDBC_DAL_IMPL)
    public NBAPlayerStatisticsDAL nbaPlayerStatisticsJDBCDALImpl() {
        return new NBAPlayerStatisticsJDBCDALImpl();
    }

    @Bean(WNBA_STAT_JPA_DAL_IMPL)
    @TODO("Not Yet Implemented")
    public WNBAPlayerStatisticsDAL wnbaPlayerStatisticsJPADALImpl() {
        return new WNBAPlayerStatisticsDALImpl();
    }
}
