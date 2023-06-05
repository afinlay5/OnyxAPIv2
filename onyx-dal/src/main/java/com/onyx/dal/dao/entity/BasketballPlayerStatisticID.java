package com.onyx.dal.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
@Embeddable
public class BasketballPlayerStatisticID implements Serializable {

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "season")
    int season;

    protected BasketballPlayerStatisticID() {
        //Per JEE Spec
    }
}
