package com.onyx.api.config;

import com.onyx.api.error.OnyxApiRESTControllerAdvice;
import com.onyx.api.intercept.config.RESTApiConfigurer;
import com.onyx.commons.annotation.TODO;
import com.onyx.commons.model.BasketballPlayerStatisticsDataStore;
import com.onyx.service.config.OnyxServiceBeans;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.util.Map;

@Import({
        OnyxApiRESTControllerAdvice.class,
        OnyxServiceBeans.class,
        RESTApiConfigurer.class,
        RestAPIControllers.class
})
@Configuration
public class OnyxApiBeans {
    public static final String DATA_SOURCE_CONNECTION_DETAILS = "DATA_SOURCE_CONNECTION_DETAILS";

    @TODO("We can grab this from a JSON file and parse using GSON later")
    @Bean(DATA_SOURCE_CONNECTION_DETAILS)
    public Map<BasketballPlayerStatisticsDataStore, DataSource> dataStoreConnectionDetails() {
        return Map.of(
                //name
                //url
                //username
                //pwd
                //driverclassname
                //hibernate dialect
        );
    }
}
