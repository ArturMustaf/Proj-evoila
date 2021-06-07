package de.test.backend.entities.repositories.container;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Repository;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Postgres connection to local postgress instance => useful for development
 */
public abstract class PostgresTest {
    private static final PostgreSQLContainer postgres;

    static {
        postgres = new PostgreSQLContainer("postgres")
                .withDatabaseName("test")
                .withUsername("postgres")
                .withPassword("password");
        postgres.start();

    }

    public static class PropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues
                    .of("spring.datasource.driver-class-name=" + postgres.getDriverClassName(),
                            "spring.datasource.url=" + postgres.getJdbcUrl(),
                            "spring.datasource.username=" + postgres.getUsername(),
                            "spring.datasource.password=" + postgres.getPassword())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
