package com.securetemplate.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManager",
        transactionManagerRef = "transactionManager",
        basePackages = "com.securetemplate.repository")
public class SecureTemplateConfig {


    @Autowired(required = false)
    private PersistenceUnitManager persistenceUnitManager;

    @Bean
    public JpaProperties reportingJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "datasource")
    public DataSource reportingDataSource() {
        return (DataSource) DataSourceBuilder.create().type(DataSource.class).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManager(
            JpaProperties reportingJpaProperties) {
        EntityManagerFactoryBuilder builder = createEntityManagerFactoryBuilder(reportingJpaProperties);
        return builder
                .dataSource(reportingDataSource())
                .packages("com.securetemplate.domain")
                .persistenceUnit("secureTemplate")
                .build();
    }

    @Bean
    @Primary
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManager) {
        return new JpaTransactionManager(entityManager);
    }

    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(JpaProperties reportingJpaProperties) {
        JpaVendorAdapter jpaVendorAdapter = createJpaVendorAdapter(reportingJpaProperties);
        return new EntityManagerFactoryBuilder(jpaVendorAdapter,
                reportingJpaProperties.getProperties(), this.persistenceUnitManager);
    }

    private JpaVendorAdapter createJpaVendorAdapter(JpaProperties jpaProperties) {
        AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(jpaProperties.isShowSql());
        adapter.setDatabase(jpaProperties.getDatabase());
        adapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
        adapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        return adapter;
    }

}
