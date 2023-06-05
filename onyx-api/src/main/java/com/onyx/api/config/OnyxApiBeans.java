package com.onyx.api.config;

import com.onyx.api.error.OnyxApiRESTControllerAdvice;
import com.onyx.api.intercept.DynamicDataSourceRoutingInterceptor;
import com.onyx.api.intercept.config.RESTApiMvcConfigurer;
import com.onyx.service.config.OnyxServiceBeans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        DynamicDataSourceRoutingInterceptor.class,
        OnyxApiRESTControllerAdvice.class,
        OnyxServiceBeans.class,
        RESTApiMvcConfigurer.class,
        RestAPIControllers.class
})
@Configuration
public class OnyxApiBeans {}
