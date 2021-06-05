package de.thbingenproj.backend.entities.repositories.container;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Initializer to write the test data generation into local database
 */
public class LocalInitalizer {
    public static class PropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues
                    .of("spring.datasource.url=" + "jdbc:postgresql://localhost:5432/postgres",
                            "spring.datasource.username=" + "postgres",
                            "spring.datasource.password=" + "postgres1234")
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
