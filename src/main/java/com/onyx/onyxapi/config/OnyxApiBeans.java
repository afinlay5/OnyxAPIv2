package com.onyx.onyxapi.config;

import com.onyx.onyxapi.exception.OnyxApiControllerAdvice;
import com.onyx.onyxapi.service.config.OnyxServiceBeans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        RestAPIControllers.class,
        OnyxApiControllerAdvice.class,
        OnyxServiceBeans.class,
})
@Configuration
public class OnyxApiBeans {}
