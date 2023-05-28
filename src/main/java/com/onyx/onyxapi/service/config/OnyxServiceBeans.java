package com.onyx.onyxapi.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(OnyxBasketballServiceBeans.class)
@Configuration
public final class OnyxServiceBeans { }
