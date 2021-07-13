package com.crawler.postgres.repository;

import com.crawler.postgres.entity.OrgUserRole;
import com.crawler.extensions.PostgresTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
class OrgUserRoleRoleRepositoryTest {

    @Autowired
    OrgUserRoleRepository orgUserRoleRepository;

    @Test
    void testGeneration(){
        Assertions.assertNotNull(orgUserRoleRepository);
    }

    @Test
    @Transactional
    void createOrgUserRole(){
        List<OrgUserRole> entities = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            var orgUserRole = new OrgUserRole();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            orgUserRole.setId(generatedString);
            entities.add(orgUserRole);
        }
        orgUserRoleRepository.saveAll(entities);
        var checkList = orgUserRoleRepository.findAll();
        for(OrgUserRole x : entities){
            Assertions.assertTrue(checkList.contains(x));
        }
    }

    @Test
    @Transactional
    void readOrgUserRole(){
        var UserRole = new OrgUserRole();
        UserRole.setId("testID");
        orgUserRoleRepository.save(UserRole);
        Assertions.assertNotNull(orgUserRoleRepository.findById(UserRole.getId()));
    }

    @Test
    @Transactional
    void deleteOrgUserRole(){
        var UserRole = new OrgUserRole();
        UserRole.setId("testID");
        orgUserRoleRepository.save(UserRole);
        Assertions.assertNotNull(orgUserRoleRepository.findById(UserRole.getId()));
        orgUserRoleRepository.delete(UserRole);
        Assertions.assertNull(orgUserRoleRepository.findById(UserRole.getId()).orElse(null));
    }

    @Test
    @Transactional
    void updateOrgUserRole(){
        var UserRole = new OrgUserRole();
        UserRole.setName("testName");
        var orgUserRoleInitialName = UserRole.getName();
        UserRole.setId("testID");
        orgUserRoleRepository.save(UserRole);
        Assertions.assertNotNull(orgUserRoleRepository.findById(UserRole.getId()));
        var orgUserRoleReturned = orgUserRoleRepository.findById(UserRole.getId()).orElse(null);
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        orgUserRoleReturned.setName(generatedString);
        orgUserRoleRepository.save(orgUserRoleReturned);
        assertNotEquals(orgUserRoleInitialName, orgUserRoleRepository.findById(UserRole.getId()).orElse(null).getName());
    }

    @Test
    @Transactional
    void findAllOrgUserRoles() {
        var entities = new ArrayList<OrgUserRole>();
        for(int i = 0; i < 50; i++){
            var orgUserRole = new OrgUserRole();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            orgUserRole.setId(generatedString);
            entities.add(orgUserRole);
        }
        orgUserRoleRepository.saveAll(entities);
        var list= orgUserRoleRepository.findAll();
        for(var x : entities){
            Assertions.assertTrue(list.contains(x));
        }
    }
}