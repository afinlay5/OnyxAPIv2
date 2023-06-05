package com.onyx.dal.dao.orm;

import jakarta.persistence.spi.PersistenceProvider;
import org.hibernate.jpa.HibernatePersistenceProvider;

public enum PersistenceProviders {
    HIBERNATE_PROVIDER(HibernatePersistenceProvider.class);

    private final Class<? extends PersistenceProvider> persistenceProvider;

    PersistenceProviders(Class<? extends PersistenceProvider> persistenceProvider) {
        this.persistenceProvider = persistenceProvider;
    }

    public Class<? extends PersistenceProvider> provider() {
        return persistenceProvider;
    }
}
