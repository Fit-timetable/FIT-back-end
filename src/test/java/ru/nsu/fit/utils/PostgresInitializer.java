package ru.nsu.fit.utils;


import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Инициализирует тестовый контейнер с postgreSQL
 */
public class PostgresInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                "spring.datasource.url=" + DefaultPostgresSqlContainer.getInstance().getJdbcUrl(),
                "spring.datasource.username=" + DefaultPostgresSqlContainer.USERNAME,
                "spring.datasource.password=" + DefaultPostgresSqlContainer.PASSWORD,
                "spring.flyway.url=" + DefaultPostgresSqlContainer.getInstance().getJdbcUrl(),
                "spring.flyway.user=" + DefaultPostgresSqlContainer.USERNAME,
                "spring.flyway.password=" + DefaultPostgresSqlContainer.PASSWORD
        ).applyTo(applicationContext.getEnvironment());
    }
}
