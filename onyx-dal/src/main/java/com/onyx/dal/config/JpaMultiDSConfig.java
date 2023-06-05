package com.onyx.dal.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

import static com.onyx.commons.util.Constants.JPA_DATA_SOURCE;
import static com.onyx.commons.util.Constants.JPA_ENTITY_PACKAGE;
import static java.util.Objects.requireNonNull;

@Configuration
public class JpaMultiDSConfig {
    @Bean("transactionManager")
    public JpaTransactionManager getTransactionManager(EntityManagerFactory entityManagerFactory) {
        val transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            @Qualifier(JPA_DATA_SOURCE) DataSource dataSource,
            @Qualifier(JPA_ENTITY_PACKAGE) String jpaEntityPackage,
            Environment environment) {
        requireNonNull(dataSource, "dataSource was required and is missing");
        val entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan(jpaEntityPackage);
        entityManagerFactoryBean.setJpaProperties(additionalJpaProperties(environment));
        val vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);

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
