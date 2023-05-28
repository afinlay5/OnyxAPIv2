package com.onyx.onyxapi.service.config;

import com.onyx.onyxapi.commons.config.MetadataCommonBeans;
import com.onyx.onyxapi.service.NBABasketballReferenceDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.ExecutorService;

@Import(MetadataCommonBeans.class)
@Configuration
public class OnyxBasketballServiceBeans {

    @Bean
    public NBABasketballReferenceDataSource nbaBasketballReferenceDataSource(ExecutorService executorService) {
        return  new NBABasketballReferenceDataSource(executorService);
    }
}
