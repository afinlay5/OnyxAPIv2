package com.onyx.dal.impl;

import com.onyx.commons.model.BasketballPlayerStatisticsProfile;
import com.onyx.dal.NBAPlayerStatisticsDAL;

import java.util.concurrent.CompletableFuture;

/* Retrieves and Persists {@link BasketballPlayerStatisticsProfile} from */
public class NBAPlayerStatisticsJPADALImpl implements NBAPlayerStatisticsDAL {

    /**
     * Persists {@code basketballPlayerStatisticsProfile} in target persistence mechanism
     *
     * @param basketballPlayerStatisticsProfile record to persist
     * @return basketballPlayerStatisticsProfile persisted record
     */
    @Override
    public CompletableFuture<BasketballPlayerStatisticsProfile> persistBasketballPlayerStats(
            BasketballPlayerStatisticsProfile basketballPlayerStatisticsProfile) {
        return null;
    }
}
