package com.onyx.dal.dao.jpa.repository;

import com.onyx.dal.dao.entity.BasketballPlayerStatisticEntity;
import com.onyx.dal.dao.entity.BasketballPlayerStatisticID;
import org.springframework.data.jpa.repository.JpaRepository;

@OnyxJpaRepo
public interface NBAPlayerStatisticsRepository extends JpaRepository<BasketballPlayerStatisticEntity, BasketballPlayerStatisticID> {
}
