package com.onyx.dal.dao.entity;

import com.onyx.commons.model.BasicBasketballPlayerStatistics;
import com.onyx.commons.model.BasketballPlayerInfo;
import com.onyx.commons.model.BasketballPlayerStatisticsProfile;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.val;

import java.time.LocalDateTime;

import static com.onyx.commons.util.Preconditions.requireNotNull;

@Builder
@Entity
@Table(name = "player_statistic")
public class BasketballPlayerStatisticEntity {
    @EmbeddedId
    BasketballPlayerStatisticID id;

    @Column(name = "dob")
    String dob;

    @Column(name = "points_per_game")
    Float ppg;

    @Column(name = "rebounds_per_game")
    Float rpg;

    @Column(name = "assists_per_game")
    Float apg;

    @Column(name = "created_by_uuid")
    String createdBy;

    @Column(name = "created_timestamp")
    LocalDateTime createdTimeStamp;

    @Column(name = "updated_by_uuid")
    String updatedBy;

    @Column(name = "updated_timestamp")
    LocalDateTime updatedTimeStamp;

    protected BasketballPlayerStatisticEntity() {
        //Per JEE Spec
    }

    public static BasketballPlayerStatisticEntity fromBasketballPlayerStatisticsProfile(BasketballPlayerStatisticsProfile basketballPlayerStatisticsProfile) {
        requireNotNull(basketballPlayerStatisticsProfile, "basketballPlayerStatisticsProfile");
        val basketballPlayerStatistics = requireNotNull(basketballPlayerStatisticsProfile.basicBasketballPlayerStatistics(), "basketballPlayerStatistics");
        val basketballPlayerInfo = requireNotNull(basketballPlayerStatisticsProfile.basketballPlayerInfo(), "basketballPlayerInfo");

        return BasketballPlayerStatisticEntity.builder()
                .id(BasketballPlayerStatisticID.builder()
                        .firstName(basketballPlayerInfo.firstName())
                        .lastName(basketballPlayerInfo.lastName())
                        .build())
                .ppg(basketballPlayerStatistics.getPpg())
                .rpg(basketballPlayerStatistics.getRpg())
                .apg(basketballPlayerStatistics.getApg())
                .createdBy("afinlay")
                .createdTimeStamp(LocalDateTime.now())
                .updatedBy("afinlay")
                .updatedTimeStamp(LocalDateTime.now())
                .build();
    }

    //Note - You can use a mapper class for this too, of course
    public BasketballPlayerStatisticsProfile toNewBasketballPlayerStatisticProfile() {
        return new BasketballPlayerStatisticsProfile(new BasketballPlayerInfo(id.getFirstName(), id.getLastName()),
                new BasicBasketballPlayerStatistics(id.getSeason(), ppg, rpg, apg));
    }
}
