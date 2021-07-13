package com.crawler.repository;

import com.crawler.entity.Organization;
import com.crawler.extensions.PostgresTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
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
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            org.setId(generatedString);
            entities.add(org);
        }
        organizationRepository.saveAll(entities);
        var checkList = organizationRepository.findAll();
        for(Organization x : entities){
            Assertions.assertTrue(checkList.contains(x));
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
    void deleteOrg(){
        var organization = new Organization();
        organization.setId("testID");
        organizationRepository.save(organization);
        Assertions.assertNotNull(organizationRepository.findById(organization.getId()));
        organizationRepository.delete(organization);
        Assertions.assertNull(organizationRepository.findById(organization.getId()).orElse(null));
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
        String generatedString = new String(array, StandardCharsets.UTF_8);
        orgReturned.setName(generatedString);
        organizationRepository.save(orgReturned);
        assertNotEquals(orgInitialName, organizationRepository.findById(organization.getId()).orElse(null).getName());
    }

    @Test
    @Transactional
    void findAllOrgs() {
        var entities = new ArrayList<Organization>();
        for(int i = 0; i < 50; i++){
            var org = new Organization();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            org.setId(generatedString);
            entities.add(org);
        }
        organizationRepository.saveAll(entities);
        var list= organizationRepository.findAll();
        for(var x : entities){
            Assertions.assertTrue(list.contains(x));
        }
    }
}