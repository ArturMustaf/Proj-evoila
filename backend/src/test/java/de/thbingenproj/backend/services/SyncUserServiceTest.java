package de.thbingenproj.backend.services;

import de.thbingenproj.backend.extensions.BeforeEvery;
import de.thbingenproj.backend.repository.OrgUserRepository;
import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


@ExtendWith(BeforeEvery.class)
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@SpringBootTest
class SyncUserServiceTest {
    @Autowired
    SyncUserService syncUserService;
    @Autowired
    OrgUserRepository orgUserRepository;



    @Test
    void testCreation(){
        Assertions.assertNotNull(syncUserService);
        Assertions.assertNotNull(orgUserRepository);
    }

}