package com.onyx.dal.dao.orm;

import org.springframework.orm.jpa.DefaultJpaDialect;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;

public enum JpaDialects {
    DEFAULT_DIALECT(new DefaultJpaDialect()),
    ECLIPSE_LINK_DIALECT(new EclipseLinkJpaDialect()),
    HIBERNATE_DIALECT(new HibernateJpaDialect());

    private final JpaDialect jpaDialect;

    JpaDialects(JpaDialect jpaDialect) {
        this.jpaDialect = jpaDialect;
    }

    public JpaDialect dialect() {
        return jpaDialect;
    }
}
