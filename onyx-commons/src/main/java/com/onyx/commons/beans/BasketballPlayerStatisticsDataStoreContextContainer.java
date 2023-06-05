package com.onyx.commons.beans;

import com.onyx.commons.model.BasketballPlayerStatisticsDataStore;

import static java.util.Objects.requireNonNull;

public class BasketballPlayerStatisticsDataStoreContextContainer {
    private static final ThreadLocal<BasketballPlayerStatisticsDataStore> DELEGATE = new ThreadLocal<>();

    private BasketballPlayerStatisticsDataStoreContextContainer() {
    }

    public static BasketballPlayerStatisticsDataStore getDelegate() {
        return DELEGATE.get();
    }

    public static void set(BasketballPlayerStatisticsDataStore dataStore) {
        BasketballPlayerStatisticsDataStoreContextContainer.unload();
        requireNonNull(dataStore, "dataStore is required and missing");
        DELEGATE.set(dataStore);
    }

    //TODO - come back around to this;
    public static void unload() {
        DELEGATE.remove();
    }
}
