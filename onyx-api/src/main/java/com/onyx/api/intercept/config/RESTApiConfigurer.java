package com.onyx.api.intercept.config;

import com.onyx.api.intercept.DynamicDataSourceRoutingInterceptor;
import com.onyx.commons.model.BasketballPlayerStatisticsDataStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import javax.sql.DataSource;
import java.util.Map;

import static com.onyx.api.config.OnyxApiBeans.DATA_SOURCE_CONNECTION_DETAILS;

@Configuration
public class RESTApiConfigurer implements WebMvcConfigurer {

    private final Map<BasketballPlayerStatisticsDataStore, DataSource> dataStoreConnectionDetails;

    public RESTApiConfigurer(@Qualifier(DATA_SOURCE_CONNECTION_DETAILS)
                             Map<BasketballPlayerStatisticsDataStore, DataSource> dataStoreConnectionDetails) {
        this.dataStoreConnectionDetails = dataStoreConnectionDetails;
    }

    /**
     * Add Spring MVC lifecycle interceptors for pre- and post-processing of
     * controller method invocations and resource handler requests.
     * Interceptors can be registered to apply to all requests or be limited
     * to a subset of URL patterns.
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                new WebRequestHandlerInterceptorAdapter(
                        new DynamicDataSourceRoutingInterceptor(dataStoreConnectionDetails)));
    }
}
