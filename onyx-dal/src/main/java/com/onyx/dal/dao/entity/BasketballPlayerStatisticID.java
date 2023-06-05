package com.onyx.dal.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Embeddable
public class BasketballPlayerStatisticID implements Serializable {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "season")
    private int season;

    @Column(name = "date_of_birth")
    private LocalDate dob;

    protected BasketballPlayerStatisticID() {
        //Per JEE Spec
    }

    private BasketballPlayerStatisticID(BasketballPlayerStatisticIDBuilder builder) {
        firstName = builder.firstName;
        lastName = builder.lastName;
        season = builder.season;
        dob = builder.dob;
    }

    public static BasketballPlayerStatisticIDBuilder builder() {
        return new BasketballPlayerStatisticIDBuilder();
    }

    public static class BasketballPlayerStatisticIDBuilder {
        private String firstName;
        private String lastName;
        private int season;
        private LocalDate dob;

        BasketballPlayerStatisticIDBuilder() {
        }

        public BasketballPlayerStatisticIDBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public BasketballPlayerStatisticIDBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public BasketballPlayerStatisticIDBuilder season(int season) {
            this.season = season;
            return this;
        }

        public BasketballPlayerStatisticIDBuilder dob(LocalDate dob) {
            this.dob = dob;
            return this;
        }

        public BasketballPlayerStatisticID build() {
            return new BasketballPlayerStatisticID(this);
        }

        public String toString() {
            return "BasketballPlayerStatisticID.BasketballPlayerStatisticIDBuilder(firstName=" + this.firstName + ", lastName=" + this.lastName + ", season=" + this.season + ")";
        }
    }
}
