package com.onyx.dal.dao.orm;

import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.function.Supplier;

public enum JPAVendorAdapters {
    ECLIPSE_LINK_ADAPTER(EclipseLinkJpaVendorAdapter::new),
    HIBERNATE_ADAPTER(HibernateJpaVendorAdapter::new);


    private final Supplier<JpaVendorAdapter> jpaVendorAdapter;

    JPAVendorAdapters(Supplier<JpaVendorAdapter> jpaVendorAdapter) {
        this.jpaVendorAdapter = jpaVendorAdapter;
    }

    public JpaVendorAdapter adapter() {
        return jpaVendorAdapter.get();
    }
}
