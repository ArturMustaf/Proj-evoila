package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entity.OrgNetwork;
import de.thbingenproj.backend.extensions.BeforeEvery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

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
            orgNetwork.setId("w-.-:w"+ i);
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
        orgNetwork.setId("testID");
        orgNetworkRepository.save(orgNetwork);
        Assertions.assertNotNull(orgNetworkRepository.findById(orgNetwork.getId()));
    }

    @Test
    @Transactional
    void updateOrgNetwork(){
        var orgNetwork = new OrgNetwork();
        orgNetwork.setName("testName");
        var orgNetworkInitialName = orgNetwork.getName();
        orgNetwork.setId("testID");
        orgNetworkRepository.save(orgNetwork);
        Assertions.assertNotNull(orgNetworkRepository.findById(orgNetwork.getId()));
        var orgNetworkReturned = orgNetworkRepository.findById(orgNetwork.getId()).orElse(null);
        orgNetworkReturned.setName("eg-.;-");
        orgNetworkRepository.save(orgNetworkReturned);
        assertNotEquals(orgNetworkInitialName, orgNetworkRepository.findById(orgNetwork.getId()).orElse(null).getName());
    }
}