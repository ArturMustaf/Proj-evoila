package de.thbingenproj.backend.unused.events;

import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.extensions.BeforeEvery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
class CreationListenerTest {
    //TODO implement or remove
    @Test
    void createRuntimePairs() {
    }

    @Test
    void checkRetried() {
    }

    @Test
    void createResourceLog() {
    }
}