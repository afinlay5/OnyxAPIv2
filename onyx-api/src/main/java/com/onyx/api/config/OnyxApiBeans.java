package com.onyx.api.config;

import com.onyx.api.exception.OnyxApiRESTControllerAdvice;
import com.onyx.service.config.OnyxServiceBeans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        RestAPIControllers.class,
        OnyxApiRESTControllerAdvice.class,
        OnyxServiceBeans.class,
})
@Configuration
public class OnyxApiBeans {}
