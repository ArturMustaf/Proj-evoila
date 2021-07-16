package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entity.ResType;
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
public class ResourceRepositoryTypeTest {
    @Autowired
    ResTypeRepository resTypeRepository;

    @Test
    void testGeneration() {
        Assertions.assertNotNull(resTypeRepository);
    }

    @Test
    @Transactional
    void createResourceType() {
        List<ResType> entities = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            var resourceType = new ResType();
            String generatedString = RandomStringUtils.random(10);
            resourceType.setId(generatedString);
            entities.add(resourceType);
        }
        resTypeRepository.saveAll(entities);
        var repositoryTypeInRepository = resTypeRepository.findAll();
        for (var type : repositoryTypeInRepository) {
            Assertions.assertTrue(entities.contains(type));
        }
    }
    @Transactional
    @Test
    void readResourceType() {
        var resourceType = new ResType();
        resourceType.setId("testID");
        resTypeRepository.save(resourceType);
        Assertions.assertNotNull(resTypeRepository.findById(resourceType.getId()));
    }
    @Transactional
    @Test
    void updateResourceType() {
        var resourceType = new ResType();
        resourceType.setTypeOfResource("CPU");
        var orgInitialName = resourceType.getTypeOfResource();
        resourceType.setId("testID");
        resTypeRepository.save(resourceType);
        Assertions.assertNotNull(resTypeRepository.findById(resourceType.getId()));
        var orgUserReturned = resTypeRepository.findById(resourceType.getId()).orElse(null);
        orgUserReturned.setTypeOfResource("ÄöUq1,.-:");
        resTypeRepository.save(orgUserReturned);
        assertNotEquals(orgInitialName, resTypeRepository.findById(resourceType.getId()).orElse(null).getTypeOfResource());
    }
}
