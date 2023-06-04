package com.onyx.dal;

import com.onyx.commons.model.BasketballPlayerStatisticsProfile;

/**
 * Specification for NBA {@link BasketballPlayerStatisticsProfile}s persistence layer data access
 * Abstracts Data Access Operations from specific DAO provider; Promotes loose coupling.
 */
public interface NBAPlayerStatisticsDAL extends BasketballLeaguePlayerStatisticsDAL {

}
