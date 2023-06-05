package com.onyx.service.config;

import com.onyx.dal.config.OnyxDALBeans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        OnyxDALBeans.class,
        OnyxBasketballServiceBeans.class
})
@Configuration
public class OnyxServiceBeans { }
