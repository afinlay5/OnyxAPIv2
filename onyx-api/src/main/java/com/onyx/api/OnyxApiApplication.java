package com.onyx.api;

import com.onyx.api.config.OnyxApiBeans;
import com.onyx.dal.dao.entity.OnyxJpaDAOEntity;
import com.onyx.dal.dao.repository.OnyxJpaRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableJpaRepositories(
        basePackageClasses = OnyxJpaRepo.class,
        includeFilters = @ComponentScan.Filter(classes = OnyxJpaRepo.class)
)
@EntityScan(basePackageClasses = OnyxJpaDAOEntity.class)
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        JdbcRepositoriesAutoConfiguration.class
})
@Import(OnyxApiBeans.class)
@SpringBootApplication
public class OnyxApiApplication {

    public static void main(@SuppressWarnings("java:S1197") String args[]) {
        SpringApplication.run(OnyxApiApplication.class, args);
    }

}

//heroku logs --app onyx-api-v2 --tail