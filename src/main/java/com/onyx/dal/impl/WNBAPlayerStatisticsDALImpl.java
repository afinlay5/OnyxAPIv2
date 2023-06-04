package com.onyx.dal.impl;

import com.onyx.commons.model.BasketballPlayerStatisticsProfile;
import com.onyx.dal.WNBAPlayerStatisticsDAL;

import java.util.concurrent.CompletableFuture;

public class WNBAPlayerStatisticsDALImpl implements WNBAPlayerStatisticsDAL {

    /**
     * Persists {@code basketballPlayerStatisticsProfile} in target persistence mechanism
     *
     * @param basketballPlayerStatisticsProfile record to persist
     * @return basketballPlayerStatisticsProfile persisted record
     */
    @Override
    public CompletableFuture<BasketballPlayerStatisticsProfile> persistBasketballPlayerStats(BasketballPlayerStatisticsProfile basketballPlayerStatisticsProfile) {
        return null;
    }
}
