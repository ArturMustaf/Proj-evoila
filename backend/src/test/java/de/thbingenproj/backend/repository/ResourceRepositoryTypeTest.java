package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
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

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
public class ResourceRepositoryTypeTest {
    @Autowired
    ResourceTypeRepository resourceTypeRepository;

    @Test
    void testGeneration() {
        Assertions.assertNotNull(resourceTypeRepository);
    }

    @Test
    @Transactional
    void createResourceType() {
        List<ResourceType> entities = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            var resourceType = new ResourceType();
            String generatedString = RandomStringUtils.random(10);
            resourceType.setId(generatedString);
            entities.add(resourceType);
        }
        resourceTypeRepository.saveAll(entities);
        var repositoryTypeInRepository = resourceTypeRepository.findAll();
        for (var type : repositoryTypeInRepository) {
            Assertions.assertTrue(entities.contains(type));
        }
    }

    @Test
    @Transactional
    void readResourceType() {
        var resourceType = new ResourceType();
        resourceType.setId("testID");
        resourceTypeRepository.save(resourceType);
        Assertions.assertNotNull(resourceTypeRepository.findById(resourceType.getId()));
    }

    @Test
    @Transactional
    void updateResourceType() {
        var resourceType = new ResourceType();
        resourceType.setTypeOfResource("CPU");
        var orgInitialName = resourceType.getTypeOfResource();
        resourceType.setId("testID");
        resourceTypeRepository.save(resourceType);
        Assertions.assertNotNull(resourceTypeRepository.findById(resourceType.getId()));
        var orgUserReturned = resourceTypeRepository.findById(resourceType.getId()).orElse(null);
        orgUserReturned.setTypeOfResource("ÄöUq1,.-:");
        resourceTypeRepository.save(orgUserReturned);
        assertNotEquals(orgInitialName, resourceTypeRepository.findById(resourceType.getId()).orElse(null).getTypeOfResource());
    }
}
