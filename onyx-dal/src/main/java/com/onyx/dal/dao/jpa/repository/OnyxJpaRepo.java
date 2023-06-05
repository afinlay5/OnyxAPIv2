package com.onyx.dal.dao.jpa.repository;

import org.springframework.stereotype.Repository;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* Marker Type for enabled JPA Repos in Onyx */
@Inherited
@Repository
@Retention(RetentionPolicy.RUNTIME)
public @interface OnyxJpaRepo {
}
