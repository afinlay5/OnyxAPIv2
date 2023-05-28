package com.onyx.onyxapi.service.config;

import com.onyx.onyxapi.service.NBABasketballReferenceDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OnyxBasketballServiceBeans {

    @Bean
    public NBABasketballReferenceDataSource nbaBasketballReferenceDataSource() {
        return  new NBABasketballReferenceDataSource();
    }
}
