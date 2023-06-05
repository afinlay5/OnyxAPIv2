package com.onyx.dal.config;

import com.onyx.commons.annotation.TODO;
import com.onyx.dal.NBAPlayerStatisticsDAL;
import com.onyx.dal.WNBAPlayerStatisticsDAL;
import com.onyx.dal.dao.entity.OnyxJpaDAOEntity;
import com.onyx.dal.dao.jpa.repository.NBAPlayerStatisticsRepository;
import com.onyx.dal.dao.jpa.repository.OnyxJpaRepo;
import com.onyx.dal.impl.NBAPlayerStatisticsJDBCDALImpl;
import com.onyx.dal.impl.NBAPlayerStatisticsJPADALImpl;
import com.onyx.dal.impl.WNBAPlayerStatisticsDALImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.concurrent.ExecutorService;

import static com.onyx.commons.util.Constants.COMMON_THREAD_POOL;

@Import(JpaMultiDSConfig.class)
@EnableJpaRepositories(
        basePackageClasses = OnyxJpaRepo.class,
        includeFilters = @ComponentScan.Filter(classes = OnyxJpaRepo.class)
)
@EntityScan(basePackageClasses = OnyxJpaDAOEntity.class)
@Configuration
public class OnyxDALBeans {
    public static final String NBA_STAT_JPA_DAL_IMPL = "NBA_STAT_JPA_DAL_IMPL";
    public static final String NBA_STAT_JDBC_DAL_IMPL = "NBA_STAT_JDBC_DAL_IMPL";
    public static final String WNBA_STAT_JPA_DAL_IMPL = "WNBA_STAT_JPA_DAL_IMPL";

    @Bean(NBA_STAT_JPA_DAL_IMPL)
    public NBAPlayerStatisticsDAL nbaPlayerStatisticsJPADALImpl(@Qualifier(COMMON_THREAD_POOL) ExecutorService executorService,
                                                                NBAPlayerStatisticsRepository nbaPlayerStatisticsRepository) {
        return new NBAPlayerStatisticsJPADALImpl(executorService, nbaPlayerStatisticsRepository);
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
