package com.onyx.dal.dao.entity;

import com.onyx.commons.model.BasicBasketballPlayerStatistics;
import com.onyx.commons.model.BasketballPlayerInfo;
import com.onyx.commons.model.BasketballPlayerStatisticsProfile;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.val;

import java.time.LocalDateTime;

import static com.onyx.commons.util.Preconditions.requireNotNull;

@Entity
@Table(name = "player_statistic")
public record BasketballPlayerStatisticEntity(
        @EmbeddedId BasketballPlayerStatisticID id,

        @Column(name = "dob")
        String dob,

        @Column(name = "points_per_game")
        Float ppg,

        @Column(name = "rebounds_per_game")
        Float rpg,

        @Column(name = "assists_per_game")
        Float apg,

        @Column(name = "created_by_uuid")
        String createdBy,

        @Column(name = "created_timestamp")
        LocalDateTime createdTimeStamp,

        @Column(name = "updated_by_uuid")
        String updatedBy,

        @Column(name = "updated_timestamp")
        LocalDateTime updatedTimeStamp
) {
    public static BasketballPlayerStatisticEntity fromBasketballPlayerStatisticsProfile(BasketballPlayerStatisticsProfile basketballPlayerStatisticsProfile) {
        requireNotNull(basketballPlayerStatisticsProfile, "basketballPlayerStatisticsProfile");
        val basketballPlayerStatistics = requireNotNull(basketballPlayerStatisticsProfile.basicBasketballPlayerStatistics(), "basketballPlayerStatistics");

        return new BasketballPlayerStatisticEntity(BasketballPlayerStatisticID.builder().build(), null, basketballPlayerStatistics.getPpg(),
                basketballPlayerStatistics.getRpg(), basketballPlayerStatistics.getApg(), "afinlay", LocalDateTime.now(), "afinlay", LocalDateTime.now());
    }

    //Note - You can use a mapper class for this too, of course
    public BasketballPlayerStatisticsProfile toNewBasketballPlayerStatisticProfile() {
        return new BasketballPlayerStatisticsProfile(new BasketballPlayerInfo(id.firstName, id.lastName),
                new BasicBasketballPlayerStatistics(id.season, ppg, rpg, apg));
    }
}
