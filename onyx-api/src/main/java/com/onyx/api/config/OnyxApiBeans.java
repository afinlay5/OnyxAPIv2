package com.onyx.api.config;

import com.onyx.api.OnyxApiApplication;
import com.onyx.api.error.OnyxApiRESTControllerAdvice;
import com.onyx.api.intercept.config.RESTApiConfigurer;
import com.onyx.api.util.JSONToDataStoreDetailsUtil;
import com.onyx.commons.model.BasketballPlayerStatisticsDataStore;
import com.onyx.service.config.OnyxServiceBeans;
import lombok.val;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.net.URL;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.onyx.commons.util.Constants.DATA_SOURCE_CONNECTION_DETAILS;

@Import({
        OnyxApiRESTControllerAdvice.class,
        OnyxServiceBeans.class,
        RESTApiConfigurer.class,
        RestAPIControllers.class
})
@Configuration
public class OnyxApiBeans {
    private static final URL DATASTORE_JSON_URL = OnyxApiApplication.class.getResource("/ds.json");

    @Bean(DATA_SOURCE_CONNECTION_DETAILS)
    public Map<BasketballPlayerStatisticsDataStore, DataSource> dataStoreConnectionDetails() {
        val dataStoreDetails = JSONToDataStoreDetailsUtil.parseDataStoreDetailsFromJsonResource(DATASTORE_JSON_URL);
        return dataStoreDetails.stream()
                .map(dataStoreDetail -> {
                    //These lines are exception prone... oh well :)
                    val dataStore = BasketballPlayerStatisticsDataStore.fromFmtAgnosticString(dataStoreDetail.name());
                    return new AbstractMap.SimpleImmutableEntry<>(dataStore,
                            DataSourceBuilder.create()
                                    .driverClassName(dataStoreDetail.driverClassName())
                                    .url(dataStoreDetail.url())
                                    .username(dataStoreDetail.username())
                                    .password(dataStoreDetail.password())
                                    .build()
                    );
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
