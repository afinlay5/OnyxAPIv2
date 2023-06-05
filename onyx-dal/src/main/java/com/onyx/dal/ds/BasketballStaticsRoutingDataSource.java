package com.onyx.dal.ds;

import com.onyx.commons.model.BasketballPlayerStatisticsDataStore;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class BasketballStaticsRoutingDataSource<T> extends AbstractRoutingDataSource {
    private final Supplier<BasketballPlayerStatisticsDataStore> lookupKey;

    public BasketballStaticsRoutingDataSource(Supplier<BasketballPlayerStatisticsDataStore> lookupKey, Map<T, DataSource> dataSources) {
        this.lookupKey = requireNonNull(lookupKey, "lookupKey is required and missing");
        requireNonNull(dataSources, "dataSources is required and missing");

        //java is a literal ridiculous for this...
        setTargetDataSources(dataSources.entrySet().stream()
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    @Override
    protected BasketballPlayerStatisticsDataStore determineCurrentLookupKey() {
        return lookupKey.get();
    }
}
