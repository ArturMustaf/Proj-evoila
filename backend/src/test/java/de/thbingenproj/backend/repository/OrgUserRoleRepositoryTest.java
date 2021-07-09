package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entity.OrgUserRole;
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
            orgUserRole.setId("H-.!2"+ i);
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
        orgUserRoleReturned.setName("hös3");
        orgUserRoleRepository.save(orgUserRoleReturned);
        assertNotEquals(orgUserRoleInitialName, orgUserRoleRepository.findById(UserRole.getId()).orElse(null).getName());
    }
}