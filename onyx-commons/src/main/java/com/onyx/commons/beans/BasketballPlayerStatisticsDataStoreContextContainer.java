package com.onyx.commons.beans;

import com.onyx.commons.model.BasketballPlayerStatisticsDataStore;

import static java.util.Objects.requireNonNull;

public class BasketballPlayerStatisticsDataStoreContextContainer {
    private static final ThreadLocal<BasketballPlayerStatisticsDataStore> CONTEXT = new ThreadLocal<>();

    private BasketballPlayerStatisticsDataStoreContextContainer() {
    }

    public static BasketballPlayerStatisticsDataStore getContext() {
        return CONTEXT.get();
    }

    public static void setContext(BasketballPlayerStatisticsDataStore dataStore) {
        requireNonNull(dataStore, "dataStore is required and missing");
        CONTEXT.set(dataStore);
    }
}
