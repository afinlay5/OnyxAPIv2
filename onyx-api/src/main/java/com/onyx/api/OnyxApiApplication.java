package com.onyx.api;

import com.onyx.api.config.OnyxApiBeans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
//@EnableAutoConfiguration(exclude = {
//        DataSourceAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class,
//        JdbcRepositoriesAutoConfiguration.class
//})
@Import(OnyxApiBeans.class)
@SpringBootApplication
public class OnyxApiApplication {

    public static void main(@SuppressWarnings("java:S1197") String args[]) {
        SpringApplication.run(OnyxApiApplication.class, args);
    }

}

//heroku logs --app onyx-api-v2 --tail