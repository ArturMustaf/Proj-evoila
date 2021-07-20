package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.ResType;
import de.thbingenproj.backend.extensions.BeforeEvery;
import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
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
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
public class ResTypeRepositoryTest {
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
        Random random=new Random();
        resourceType.setId(random.nextInt());
        resTypeRepository.save(resourceType);
        Assertions.assertNotNull(resTypeRepository.findById(resourceType.getId()));
    }
    @Transactional
    @Test
    void updateResourceType() {
        var resourceType = new ResType();
        resourceType.setTypeOfResource(RandomStringUtils.randomAlphanumeric(5));
        var orgInitialName = resourceType.getTypeOfResource();
        Random random=new Random();
        resTypeRepository.save(resourceType);
        Assertions.assertNotNull(resTypeRepository.findById(resourceType.getId()));
        var orgUserReturned = resTypeRepository.findById(resourceType.getId()).orElse(null);
        orgUserReturned.setTypeOfResource(RandomStringUtils.randomAlphanumeric(5));
        resTypeRepository.save(orgUserReturned);
        assertNotEquals(orgInitialName, resTypeRepository.findById(resourceType.getId()).orElse(null).getTypeOfResource());
    }
}
