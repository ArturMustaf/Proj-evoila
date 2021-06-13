package de.thbingenproj.backend.controller;

import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.extensions.BeforeEvery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
class SearchControllerTest {

    @Test
    void getCustomers() {
    }

    @Test
    void getContactInformations() {
    }

    @Test
    void getProjects() {
    }

    @Test
    void getResourceType() {
    }
}