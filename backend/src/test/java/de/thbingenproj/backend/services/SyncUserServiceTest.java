package de.thbingenproj.backend.services;

import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entity.OrgUser;
import de.thbingenproj.backend.extensions.BeforeEvery;
import de.thbingenproj.backend.repository.OrgUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;
import java.io.IOException;


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

    //@Test
    //only for local use
    @Transactional
    void testSync() throws IOException {
        OrgUser testUser = new OrgUser();
        testUser.setId("1");
        testUser.setEmailAddress("test@test.de");
        testUser.setName("testname@test.de");
        testUser.setOrgID("1234");
        orgUserRepository.save(testUser);
        syncUserService.initKeycloak();
        syncUserService.syncAll();
        Assertions.assertTrue(!orgUserRepository.findAll().contains(testUser));
    }

}