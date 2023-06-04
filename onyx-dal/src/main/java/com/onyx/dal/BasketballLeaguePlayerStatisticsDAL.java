package com.onyx.dal;

import com.onyx.commons.model.BasketballPlayerStatisticsProfile;

import java.util.concurrent.CompletableFuture;

/**
 * Specification for {@link BasketballPlayerStatisticsProfile} persistence layer data access
 * Abstracts Data Access Operations from specific DAO provider; Promotes loose coupling.
 */
public interface BasketballLeaguePlayerStatisticsDAL {

    /**
     * Persists {@code basketballPlayerStatisticsProfile} in target persistence mechanism
     *
     * @param basketballPlayerStatisticsProfile record to persist
     * @return basketballPlayerStatisticsProfile persisted record
     */
    CompletableFuture<BasketballPlayerStatisticsProfile> persistBasketballPlayerStats(
            BasketballPlayerStatisticsProfile basketballPlayerStatisticsProfile);
}
