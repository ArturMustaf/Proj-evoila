package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entity.Resource;
import de.thbingenproj.backend.entity.ResourceType;
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
@Transactional
@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
public class ResourceRepositoryTest {
    @Autowired
    ResourceRepository resourceRepository;

    @Test
    void testGeneration() {
        Assertions.assertNotNull(resourceRepository);
    }

    @Test

    void createResource() {
        List<Resource> entities = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            var resource = new Resource();
            String generatedString = RandomStringUtils.random(10);
            resource.setId(generatedString);
            entities.add(resource);
        }
        resourceRepository.saveAll(entities);
        var ResourcesInRepository = resourceRepository.findAll();
        for (var res :ResourcesInRepository ) {
            Assertions.assertTrue(entities.contains(res));
        }
    }

    @Test

    void readResourceType() {
        var resource = new Resource();
        resource.setId("testID");
        resourceRepository.save(resource);
        Assertions.assertNotNull(resourceRepository.findById(resource.getId()));
    }

    @Test

    void updateResourceType() {
        var resource = new Resource();
        resource.setOrgID("Ä-Ü.aqe");
        var orgInitialName = resource.getOrgID();
        resource.setId("testID");
        resourceRepository.save(resource);
        Assertions.assertNotNull(resourceRepository.findById(resource.getId()));
        var orgUserReturned = resourceRepository.findById(resource.getId()).orElse(null);
        orgUserReturned.setOrgID("ÄöUq1,.-:");
        resourceRepository.save(orgUserReturned);
        assertNotEquals(orgInitialName, resourceRepository.findById(resource.getId()).orElse(null).getOrgID());
    }
}
