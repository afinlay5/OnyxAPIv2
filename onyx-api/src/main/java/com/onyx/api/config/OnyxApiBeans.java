package com.onyx.api.config;

import com.onyx.api.error.OnyxApiRESTControllerAdvice;
import com.onyx.api.intercept.config.RESTApiConfigurer;
import com.onyx.service.config.OnyxServiceBeans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        OnyxApiRESTControllerAdvice.class,
        OnyxServiceBeans.class,
        RESTApiConfigurer.class,
        RestAPIControllers.class
})
@Configuration
public class OnyxApiBeans {}
