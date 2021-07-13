package com.crawler.repository;

import com.crawler.entity.OrgUser;
import com.crawler.extensions.PostgresTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
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
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            orgUser.setId(generatedString);
            entities.add(orgUser);
        }
        orgUserRepository.saveAll(entities);
        var checkList = orgUserRepository.findAll();
        for(OrgUser x : entities){
            Assertions.assertTrue(checkList.contains(x));
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
    void deleteOrgUser(){
        var orgUser = new OrgUser();
        orgUser.setId("testID");
        orgUserRepository.save(orgUser);
        Assertions.assertNotNull(orgUserRepository.findById(orgUser.getId()));
        orgUserRepository.delete(orgUser);
        Assertions.assertNull(orgUserRepository.findById(orgUser.getId()).orElse(null));
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
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, StandardCharsets.UTF_8);
        orgUserReturned.setName(generatedString);
        orgUserRepository.save(orgUserReturned);
        assertNotEquals(orgInitialName, orgUserRepository.findById(orgUser.getId()).orElse(null).getName());
    }

    @Test
    @Transactional
    void findAllOrgUsers() {
        var entities = new ArrayList<OrgUser>();
        for(int i = 0; i < 50; i++){
            var orgUser = new OrgUser();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            orgUser.setId(generatedString);
            entities.add(orgUser);
        }
        orgUserRepository.saveAll(entities);
        var list= orgUserRepository.findAll();
        for(var x : entities){
            Assertions.assertTrue(list.contains(x));
        }
    }
}