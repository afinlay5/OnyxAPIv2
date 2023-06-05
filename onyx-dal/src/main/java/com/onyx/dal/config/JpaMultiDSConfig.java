package com.onyx.dal.config;

import com.onyx.commons.model.BasketballPlayerStatisticsDataStore;
import com.onyx.dal.dao.entity.OnyxJpaDAOEntity;
import com.onyx.dal.dao.orm.JPADialects;
import com.onyx.dal.dao.orm.JPAVendorAdapters;
import com.onyx.dal.dao.orm.PersistenceProviders;
import jakarta.persistence.EntityManagerFactory;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

import static com.onyx.commons.util.Constants.DATA_SOURCE_CONNECTION_DETAILS;
import static com.onyx.commons.util.Preconditions.requireMapNotEmpty;

@Configuration
public class JpaMultiDSConfig {
    private static final String JPA_ENTITY_PACKAGE = OnyxJpaDAOEntity.class.getPackageName();

    @Bean("transactionManager")
    public JpaTransactionManager getTransactionManager(EntityManagerFactory entityManagerFactory) {
        val transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            @Qualifier(DATA_SOURCE_CONNECTION_DETAILS) Map<BasketballPlayerStatisticsDataStore, DataSource> dataSourceConnectionDetails,
            Environment environment) {
        requireMapNotEmpty(dataSourceConnectionDetails, "dataSourceConnectionDetails was required and is missing");

        val entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        //TODO - This is broken!
//        entityManagerFactoryBean.setDataSource(new BasketballStatisticsRoutingDataSource<>(dataSourceConnectionDetails));
        entityManagerFactoryBean.setDataSource(DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:testdb")
                .username("sa")
                .password("password")
                .build());
        entityManagerFactoryBean.setPersistenceProviderClass(
                PersistenceProviders.valueOf(environment.getProperty("ds.persistenceProvider")).provider());
        entityManagerFactoryBean.setJpaDialect(JPADialects.valueOf(environment.getProperty("ds.jpaDialect")).dialect());
        entityManagerFactoryBean.setPackagesToScan(JPA_ENTITY_PACKAGE);
        entityManagerFactoryBean.setJpaProperties(additionalJpaProperties(environment));
        entityManagerFactoryBean.setJpaVendorAdapter(JPAVendorAdapters.valueOf(environment.getProperty("ds.jpaVendorAdapter")).adapter());

        return entityManagerFactoryBean;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor() {
        return  new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties additionalJpaProperties(Environment environment) {
        val additionalJpaProperties = new Properties();
        additionalJpaProperties.put("hibernate.show_sql",
                environment.getProperty("ds.hibernate.show_sql", ""));
        additionalJpaProperties.put("hibernate.format_sql",
                environment.getProperty("ds.hibernate.format_sql", ""));
        additionalJpaProperties.put("jdbc.lob.non_contextual_creation",
                environment.getProperty("ds.jdbc.lob.non_contextual_creation", ""));
        return additionalJpaProperties;
    }

}
