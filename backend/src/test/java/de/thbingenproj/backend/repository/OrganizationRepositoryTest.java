package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.Organization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
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
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
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
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        orgReturned.setName(generatedString);
        organizationRepository.save(orgReturned);
        assertNotEquals(orgInitialName, organizationRepository.findById(organization.getId()).orElse(null).getName());
    }
}