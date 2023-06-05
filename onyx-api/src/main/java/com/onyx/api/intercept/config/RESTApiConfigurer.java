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

@Configuration
public class RESTApiConfigurer implements WebMvcConfigurer {

    private final Map<BasketballPlayerStatisticsDataStore, DataSource> dataStoreMap;

    public RESTApiConfigurer(@Qualifier Map<BasketballPlayerStatisticsDataStore, DataSource> dataStoreMap) {
        this.dataStoreMap = dataStoreMap;
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
                        new DynamicDataSourceRoutingInterceptor(dataStoreMap)));
    }
}
