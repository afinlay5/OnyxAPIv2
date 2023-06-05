package com.onyx.dal;

import com.onyx.commons.beans.BasketballPlayerStatisticsDataStoreContextContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.onyx.dal.config.OnyxDALBeans.NBA_STAT_JDBC_DAL_IMPL;
import static com.onyx.dal.config.OnyxDALBeans.NBA_STAT_JPA_DAL_IMPL;
import static com.onyx.dal.config.OnyxDALBeans.WNBA_STAT_JPA_DAL_IMPL;


@Service
public final class BasketballPlayerStatisticsDALFactory {
    private final NBAPlayerStatisticsDAL nbaPlayerStatisticsJPADALImpl;
    private final NBAPlayerStatisticsDAL nbaPlayerStatisticsJDBCDALImpl;
    private final WNBAPlayerStatisticsDAL wnbaPlayerStatisticsJPADALImpl;

    public BasketballPlayerStatisticsDALFactory(@Qualifier(NBA_STAT_JPA_DAL_IMPL) NBAPlayerStatisticsDAL nbaPlayerStatisticsJPADALImpl,
                                                @Qualifier(NBA_STAT_JDBC_DAL_IMPL) NBAPlayerStatisticsDAL nbaPlayerStatisticsJDBCDALImpl,
                                                @Qualifier(WNBA_STAT_JPA_DAL_IMPL) WNBAPlayerStatisticsDAL wnbaPlayerStatisticsJPADALImpl) {
        this.nbaPlayerStatisticsJPADALImpl = nbaPlayerStatisticsJPADALImpl;
        this.nbaPlayerStatisticsJDBCDALImpl = nbaPlayerStatisticsJDBCDALImpl;
        this.wnbaPlayerStatisticsJPADALImpl = wnbaPlayerStatisticsJPADALImpl;
    }


    public NBAPlayerStatisticsDAL getNbaPlayerStatisticsDAL() {
        return switch (BasketballPlayerStatisticsDataStoreContextContainer.getContext()) {
            case MYDS1 -> nbaPlayerStatisticsJPADALImpl;
            case MYDS2 -> nbaPlayerStatisticsJDBCDALImpl;
        };
    }
}
