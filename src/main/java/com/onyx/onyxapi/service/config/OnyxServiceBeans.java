package com.onyx.onyxapi.service.config;

import com.onyx.onyxapi.dao.config.OnyxDAOBeans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        OnyxDAOBeans.class,
        OnyxBasketballServiceBeans.class
})
@Configuration
public class OnyxServiceBeans { }
