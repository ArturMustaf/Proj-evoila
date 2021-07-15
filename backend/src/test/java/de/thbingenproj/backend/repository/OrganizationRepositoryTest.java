package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entity.Organization;
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
class OrganizationRepositoryTest {

    @Autowired
    OrganizationRepository organizationRepository;

    @Test
    void testGeneration(){
        Assertions.assertNotNull(organizationRepository);
    }

    @Test
    @Transactional
    void createOrg(){
        List<Organization> entities = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            var org = new Organization();
            String generatedString = RandomStringUtils.random(10);

            org.setId(generatedString);
            entities.add(org);
        }
        organizationRepository.saveAll(entities);
        var organizationsInRepository = organizationRepository.findAll();
        for(var organization : organizationsInRepository){
            Assertions.assertTrue(entities.contains(organization));
        }
    }

    @Test
    @Transactional
    void readOrg(){
        var organization = new Organization();
        organization.setId("testID");
        organizationRepository.save(organization);
        Assertions.assertNotNull(organizationRepository.findById(organization.getId()));
    }

    @Test
    @Transactional
    void updateOrg(){
        var organization = new Organization();
        organization.setName("testName");
        var orgInitialName = organization.getName();
        organization.setId("testID");
        organizationRepository.save(organization);
        Assertions.assertNotNull(organizationRepository.findById(organization.getId()));
        var orgReturned = organizationRepository.findById(organization.getId()).orElse(null);
        orgReturned.setName("1-ÄöOtsdhj");
        organizationRepository.save(orgReturned);
        assertNotEquals(orgInitialName, organizationRepository.findById(organization.getId()).orElse(null).getName());
    }
}