package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entity.OrgUser;
import de.thbingenproj.backend.extensions.BeforeEvery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;


@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
class OrgUserRepositoryTest {

    @Autowired
    OrgUserRepository orgUserRepository;

    @Test
    void testGeneration(){
        Assertions.assertNotNull(orgUserRepository);
    }

    @Test
    @Transactional
    void createOrgUser(){
        List<OrgUser> entities = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            var orgUser = new OrgUser();
            String generatedString = RandomStringUtils.random(10);
            orgUser.setId(generatedString);
            entities.add(orgUser);
        }
        orgUserRepository.saveAll(entities);
        var orgUserInRepository = orgUserRepository.findAll();
        for(var orgUser : orgUserInRepository){
            Assertions.assertTrue(entities.contains(orgUser));
        }
    }

    @Test
    @Transactional
    void readOrgUser(){
        var orgUser = new OrgUser();
        orgUser.setId("testID");
        orgUserRepository.save(orgUser);
        Assertions.assertNotNull(orgUserRepository.findById(orgUser.getId()));
    }

    @Test
    @Transactional
    void updateOrgUser(){
        var orgUser = new OrgUser();
        orgUser.setName("testName");
        var orgInitialName = orgUser.getName();
        orgUser.setId("testID");
        orgUserRepository.save(orgUser);
        Assertions.assertNotNull(orgUserRepository.findById(orgUser.getId()));
        var orgUserReturned = orgUserRepository.findById(orgUser.getId()).orElse(null);
        orgUserReturned.setName("ÄöUq1,.-:");
        orgUserRepository.save(orgUserReturned);
        assertNotEquals(orgInitialName, orgUserRepository.findById(orgUser.getId()).orElse(null).getName());
    }
}