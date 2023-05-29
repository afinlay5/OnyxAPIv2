package com.onyx.onyxapi.config;

import com.onyx.onyxapi.exception.OnyxApiControllerAdvice;
import com.onyx.onyxapi.service.config.OnyxServiceBeans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({OnyxServiceBeans.class, OnyxApiControllerAdvice.class})
@Configuration
public class OnyxApiBeans {}
