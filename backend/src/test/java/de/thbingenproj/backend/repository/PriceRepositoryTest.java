package de.thbingenproj.backend.repository;


import de.thbingenproj.backend.entity.Prices;
import de.thbingenproj.backend.entity.ResType;
import de.thbingenproj.backend.extensions.BeforeEvery;
import de.thbingenproj.backend.unused.entities.repositories.PriceRepository;
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

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
public class PriceRepositoryTest {
    @Autowired
    PricesRepository resTypeRepository;

    @Test
    void testGeneration() {
        Assertions.assertNotNull(resTypeRepository);
    }

    @Test
    @Transactional
    void createResourceType() {
        List<Prices> entities = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            var prices = new Prices();
            String generatedString = RandomStringUtils.random(10);
            prices.setId(generatedString);
            entities.add(prices);
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
        var prices = new Prices();
        prices.setId("testID");
        resTypeRepository.save(prices);
        Assertions.assertNotNull(resTypeRepository.findById(prices.getId()));
    }
    @Transactional
    @Test
    void updateResourceType() {
        var resourceType = new Prices();
        resourceType.setResourceSize("500MB");
        var orgInitialName = resourceType.getResourceSize();
        resourceType.setId("testID");
        resTypeRepository.save(resourceType);
        Assertions.assertNotNull(resTypeRepository.findById(resourceType.getId()));
        var orgUserReturned = resTypeRepository.findById(resourceType.getId()).orElse(null);
        orgUserReturned.setResourceSize("10000MB");
        resTypeRepository.save(orgUserReturned);
        assertNotEquals(orgInitialName, resTypeRepository.findById(resourceType.getId()).orElse(null).getResourceSize());
    }

}
