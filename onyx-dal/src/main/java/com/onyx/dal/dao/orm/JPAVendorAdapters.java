package com.onyx.dal.dao.orm;

import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

public enum JPAVendorAdapters {
    ECLIPSE_LINK_ADAPTER(new EclipseLinkJpaVendorAdapter()),
    HIBERNATE_ADAPTER(new HibernateJpaVendorAdapter());


    private final JpaVendorAdapter jpaVendorAdapter;

    JPAVendorAdapters(JpaVendorAdapter jpaVendorAdapter) {
        this.jpaVendorAdapter = jpaVendorAdapter;
    }

    public JpaVendorAdapter adapter() {
        return jpaVendorAdapter;
    }
}
