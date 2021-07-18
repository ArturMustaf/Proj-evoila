package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entity.OrgNetwork;
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
class OrgNetworkRepositoryTest {

    @Autowired
    OrgNetworkRepository orgNetworkRepository;

    @Test
    void testGeneration(){
        Assertions.assertNotNull(orgNetworkRepository);
    }

    @Test
    @Transactional
    void createOrgNetwork(){
        List<OrgNetwork> entities = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            var orgNetwork = new OrgNetwork();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            orgNetwork.setId(generatedString);
            entities.add(orgNetwork);
        }
        orgNetworkRepository.saveAll(entities);
        var orgNetworksInRepository = orgNetworkRepository.findAll();
        for(var orgNetwork : orgNetworksInRepository){
            Assertions.assertTrue(entities.contains(orgNetwork));
        }
    }

    @Test
    @Transactional
    void readOrgNetwork(){
        var orgNetwork = new OrgNetwork();
        orgNetwork.setId(RandomStringUtils.randomAlphanumeric(10));
        orgNetworkRepository.save(orgNetwork);
        Assertions.assertNotNull(orgNetworkRepository.findById(orgNetwork.getId()));
    }

    @Test
    @Transactional
    void updateOrgNetwork(){
        var orgNetwork = new OrgNetwork();
        orgNetwork.setName(RandomStringUtils.randomAlphanumeric(10));
        var orgNetworkInitialName = orgNetwork.getName();
        orgNetwork.setId(RandomStringUtils.randomAlphanumeric(10));
        orgNetworkRepository.save(orgNetwork);
        Assertions.assertNotNull(orgNetworkRepository.findById(orgNetwork.getId()));
        var orgNetworkReturned = orgNetworkRepository.findById(orgNetwork.getId()).orElse(null);
        orgNetworkReturned.setName(RandomStringUtils.randomAlphanumeric(10));
        orgNetworkRepository.save(orgNetworkReturned);
        assertNotEquals(orgNetworkInitialName, orgNetworkRepository.findById(orgNetwork.getId()).orElse(null).getName());
    }
}