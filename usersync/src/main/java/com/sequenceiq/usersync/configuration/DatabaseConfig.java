package com.sequenceiq.usersync.configuration;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sequenceiq.usersync.service.ha.UsersyncNodeConfig;
import com.sequenceiq.usersync.util.DatabaseUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    @Value("${usersync.db.env.user:}")
    private String dbUser;

    @Value("${usersync.db.env.pass:}")
    private String dbPassword;

    @Value("${usersync.db.env.db:}")
    private String dbName;

    @Value("${usersync.db.env.poolsize:10}")
    private int poolSize;

    @Value("${usersync.db.env.connectiontimeout:30}")
    private long connectionTimeout;

    @Value("${usersync.db.env.minidle:2}")
    private int minimumIdle;

    @Value("${usersync.db.env.idletimeout:10}")
    private long idleTimeout;

    @Value("${usersync.db.env.schema:" + DatabaseUtil.DEFAULT_SCHEMA_NAME + '}')
    private String dbSchemaName;

    @Value("${usersync.db.env.ssl:}")
    private boolean ssl;

    @Value("#{'${usersync.cert.dir:}/${usersync.db.env.cert.file:}'}")
    private String certFile;

    @Value("${usersync.hbm2ddl.strategy:validate}")
    private String hbm2ddlStrategy;

    @Value("${usersync.hibernate.debug:false}")
    private boolean debug;

    @Inject
    @Named("databaseAddress")
    private String databaseAddress;

    @Inject
    private UsersyncNodeConfig usersyncNodeConfig;

    @Bean
    public DataSource dataSource() throws SQLException {
        DatabaseUtil.createSchemaIfNeeded("postgresql", databaseAddress, dbName, dbUser, dbPassword, dbSchemaName);
        HikariConfig config = new HikariConfig();
        if (ssl && Files.exists(Paths.get(certFile))) {
            config.addDataSourceProperty("ssl", "true");
            config.addDataSourceProperty("sslfactory", "org.postgresql.ssl.SingleCertValidatingFactory");
            config.addDataSourceProperty("sslfactoryarg", "file://" + certFile);
        }
        if (usersyncNodeConfig.isNodeIdSpecified()) {
            config.addDataSourceProperty("ApplicationName", usersyncNodeConfig.getId());
        }
        config.setJdbcUrl(String.format("jdbc:postgresql://%s/%s?currentSchema=%s", databaseAddress, dbName, dbSchemaName));
        config.setUsername(dbUser);
        config.setPassword(dbPassword);
        config.setMaximumPoolSize(poolSize);
        config.setMinimumIdle(minimumIdle);
        config.setConnectionTimeout(SECONDS.toMillis(connectionTimeout));
        config.setIdleTimeout(MINUTES.toMillis(idleTimeout));
        return new HikariDataSource(config);
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws SQLException {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory());
        jpaTransactionManager.afterPropertiesSet();
        return jpaTransactionManager;
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    @DependsOn("databaseUpMigration")
    public EntityManagerFactory entityManagerFactory() throws SQLException {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setPackagesToScan("com.sequenceiq.usersync.domain");
        entityManagerFactory.setDataSource(dataSource());

        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactory.setJpaProperties(jpaProperties());
        entityManagerFactory.afterPropertiesSet();
        return entityManagerFactory.getObject();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        return hibernateJpaVendorAdapter;
    }

    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddlStrategy);
        properties.setProperty("hibernate.show_sql", Boolean.toString(debug));
        properties.setProperty("hibernate.format_sql", Boolean.toString(debug));
        properties.setProperty("hibernate.use_sql_comments", Boolean.toString(debug));
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.default_schema", dbSchemaName);
        properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", Boolean.toString(true));
        return properties;
    }
}
