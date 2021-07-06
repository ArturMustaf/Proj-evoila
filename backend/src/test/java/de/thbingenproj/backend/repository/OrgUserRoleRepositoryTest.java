package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.OrgUserRole;
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
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
            orgUserRole.setId(generatedString);
            entities.add(orgUserRole);
        }
        orgUserRoleRepository.saveAll(entities);
        var orgUserRoleInRepository = orgUserRoleRepository.findAll();
        for(var orgUserRole : orgUserRoleInRepository){
            Assertions.assertTrue(entities.contains(orgUserRole));
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
}