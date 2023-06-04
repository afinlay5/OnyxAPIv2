package com.onyx.dal;

import com.onyx.commons.model.BasketballPlayerStatisticsDataStore;
import org.springframework.stereotype.Service;

import static com.onyx.commons.util.Preconditions.requireNotNull;

@Service
@SuppressWarnings("java:S1659") //Idc about this.
public final class BasketballPlayerStatisticsDALFactory {
    private final NBAPlayerStatisticsDAL nbaPlayerStatisticsDS1DALImpl, nbaPlayerStatisticsDS2DALImpl;
    private final WNBAPlayerStatisticsDAL wnbaPlayerStatisticsDALImpl1; //TODO Change name

    public BasketballPlayerStatisticsDALFactory(NBAPlayerStatisticsDAL nbaPlayerStatisticsDS1DALImpl,
                                                NBAPlayerStatisticsDAL nbaPlayerStatisticsDS2DALImpl,
                                                WNBAPlayerStatisticsDAL wnbaPlayerStatisticsDALImpl1) {
        this.nbaPlayerStatisticsDS1DALImpl = requireNotNull(nbaPlayerStatisticsDS1DALImpl, "nbaPlayerStatisticsDS1DALImpl");
        this.nbaPlayerStatisticsDS2DALImpl = requireNotNull(nbaPlayerStatisticsDS2DALImpl, "nbaPlayerStatisticsDS2DALImpl");
        this.wnbaPlayerStatisticsDALImpl1 = requireNotNull(wnbaPlayerStatisticsDALImpl1, "wnbaPlayerStatisticsDALImpl1");

    }

    public NBAPlayerStatisticsDAL getNbaPlayerStatisticsDAL(BasketballPlayerStatisticsDataStore dataStore) {
        return switch (dataStore) {
            case MYDS1 -> nbaPlayerStatisticsDS1DALImpl;
            case MYDS2 -> nbaPlayerStatisticsDS2DALImpl;
        };
    }
}
