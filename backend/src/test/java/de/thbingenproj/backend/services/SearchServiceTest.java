package de.thbingenproj.backend.services;

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
class SearchServiceTest {

    @Test
    void getUserOnQuery() {
    }

    @Test
    void getProjects() {
    }

    @Test
    void getContactInformations() {
    }

    @Test
    void getResourceType() {
    }
}