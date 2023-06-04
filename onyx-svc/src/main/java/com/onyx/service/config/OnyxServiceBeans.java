package com.onyx.service.config;

import com.onyx.dal.config.OnyxDAOBeans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        OnyxDAOBeans.class,
        OnyxBasketballServiceBeans.class
})
@Configuration
public class OnyxServiceBeans { }
