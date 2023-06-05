package com.onyx.dal.ds;

import com.onyx.commons.beans.BasketballPlayerStatisticsDataStoreContextContainer;
import com.onyx.commons.model.BasketballPlayerStatisticsDataStore;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class BasketballStatisticsRoutingDataSource<T> extends AbstractRoutingDataSource {

    public BasketballStatisticsRoutingDataSource(Map<T, DataSource> dataSources) {
        requireNonNull(dataSources, "dataSources is required and missing");

        //java is a little ridiculous for this...
        setTargetDataSources(dataSources.entrySet().stream()
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    @Override
    protected BasketballPlayerStatisticsDataStore determineCurrentLookupKey() {
        return BasketballPlayerStatisticsDataStoreContextContainer.getDelegate();
    }
}
