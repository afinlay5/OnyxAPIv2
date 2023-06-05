package com.onyx.dal.impl;

import com.onyx.commons.model.BasketballPlayerStatisticsProfile;
import com.onyx.dal.NBAPlayerStatisticsDAL;
import com.onyx.dal.dao.jpa.repository.NBAPlayerStatisticsRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static com.onyx.commons.util.Preconditions.requireExecutorService;
import static com.onyx.commons.util.Preconditions.requireNotNull;

/* Retrieves and Persists NBA {@link BasketballPlayerStatisticsProfile} data
 *  <p> from a dynamically determined datastore source (see {@link XYZ} for more ) </p>
 *  <p> via Spring Data JPA <p>
 * */
@Slf4j
public class NBAPlayerStatisticsJPADALImpl implements NBAPlayerStatisticsDAL {
    private final ExecutorService executorService;
    private final NBAPlayerStatisticsRepository nbaPlayerStatisticsRepository;

    public NBAPlayerStatisticsJPADALImpl(ExecutorService executorService,
                                         NBAPlayerStatisticsRepository nbaPlayerStatisticsRepository) {
        this.executorService = requireExecutorService(executorService);
        this.nbaPlayerStatisticsRepository = requireNotNull(nbaPlayerStatisticsRepository, "nbaPlayerStatisticsRepository is required and missing");
    }

    /**
     * Persists {@code basketballPlayerStatisticsProfile} in target persistence mechanism
     *
     * @param basketballPlayerStatisticsProfile record to persist
     * @return basketballPlayerStatisticsProfile persisted record
     */
    @Override
    public CompletableFuture<BasketballPlayerStatisticsProfile> persistBasketballPlayerStats(
            BasketballPlayerStatisticsProfile basketballPlayerStatisticsProfile) {
        log.info("Persisting NBA Player Statistics Profiles");
        val
        return CompletableFuture.supplyAsync(() -> nbaPlayerStatisticsRepository.saveAll(), executorService);
    }
}
