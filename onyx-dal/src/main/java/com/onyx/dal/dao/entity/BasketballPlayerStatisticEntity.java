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
public class BasketballPlayerStatisticEntity {
    @EmbeddedId
    BasketballPlayerStatisticID id;

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

    private BasketballPlayerStatisticEntity(BasketballPlayerStatisticEntityBuilder builder) {
        id = builder.id;
        ppg = builder.ppg;
        rpg = builder.rpg;
        apg = builder.apg;
        createdBy = builder.createdBy;
        createdTimeStamp = builder.createdTimeStamp;
        updatedBy = builder.updatedBy;
        updatedTimeStamp = builder.updatedTimeStamp;
    }

    public static BasketballPlayerStatisticEntity fromBasketballPlayerStatisticsProfile(BasketballPlayerStatisticsProfile basketballPlayerStatisticsProfile) {
        requireNotNull(basketballPlayerStatisticsProfile, "basketballPlayerStatisticsProfile");
        val basketballPlayerStatistics = requireNotNull(basketballPlayerStatisticsProfile.basicBasketballPlayerStatistics(), "basketballPlayerStatistics");
        val basketballPlayerInfo = requireNotNull(basketballPlayerStatisticsProfile.basketballPlayerInfo(), "basketballPlayerInfo");

        return BasketballPlayerStatisticEntity.builder()
                .id(BasketballPlayerStatisticID.builder()
                        .firstName(basketballPlayerInfo.firstName())
                        .lastName(basketballPlayerInfo.lastName())
                        .season(basketballPlayerStatistics.season())
                        .build())
                .ppg(basketballPlayerStatistics.ppg())
                .rpg(basketballPlayerStatistics.rpg())
                .apg(basketballPlayerStatistics.apg())
                .createdBy("afinlay")
                .createdTimeStamp(LocalDateTime.now())
                .updatedBy("afinlay")
                .updatedTimeStamp(LocalDateTime.now())
                .build();
    }

    public static BasketballPlayerStatisticEntityBuilder builder() {
        return new BasketballPlayerStatisticEntityBuilder();
    }

    //Note - You can use a mapper class for this too, of course
    public BasketballPlayerStatisticsProfile toNewBasketballPlayerStatisticProfile() {
        return new BasketballPlayerStatisticsProfile(new BasketballPlayerInfo(id.getFirstName(), id.getLastName(), id.getDob()),
                new BasicBasketballPlayerStatistics(id.getSeason(), ppg, rpg, apg));
    }

    public static class BasketballPlayerStatisticEntityBuilder {
        private BasketballPlayerStatisticID id;
        private Float ppg;
        private Float rpg;
        private Float apg;
        private String createdBy;
        private LocalDateTime createdTimeStamp;
        private String updatedBy;
        private LocalDateTime updatedTimeStamp;

        BasketballPlayerStatisticEntityBuilder() {
        }

        public BasketballPlayerStatisticEntityBuilder id(BasketballPlayerStatisticID id) {
            this.id = id;
            return this;
        }

        public BasketballPlayerStatisticEntityBuilder ppg(Float ppg) {
            this.ppg = ppg;
            return this;
        }

        public BasketballPlayerStatisticEntityBuilder rpg(Float rpg) {
            this.rpg = rpg;
            return this;
        }

        public BasketballPlayerStatisticEntityBuilder apg(Float apg) {
            this.apg = apg;
            return this;
        }

        public BasketballPlayerStatisticEntityBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public BasketballPlayerStatisticEntityBuilder createdTimeStamp(LocalDateTime createdTimeStamp) {
            this.createdTimeStamp = createdTimeStamp;
            return this;
        }

        public BasketballPlayerStatisticEntityBuilder updatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
            return this;
        }

        public BasketballPlayerStatisticEntityBuilder updatedTimeStamp(LocalDateTime updatedTimeStamp) {
            this.updatedTimeStamp = updatedTimeStamp;
            return this;
        }

        public BasketballPlayerStatisticEntity build() {
            return new BasketballPlayerStatisticEntity(this);
        }

        public String toString() {
            return "BasketballPlayerStatisticEntity.BasketballPlayerStatisticEntityBuilder(id=" + this.id + ", ppg=" + this.ppg + ", rpg=" + this.rpg + ", apg=" + this.apg + ", createdBy=" + this.createdBy + ", createdTimeStamp=" + this.createdTimeStamp + ", updatedBy=" + this.updatedBy + ", updatedTimeStamp=" + this.updatedTimeStamp + ")";
        }
    }
}
