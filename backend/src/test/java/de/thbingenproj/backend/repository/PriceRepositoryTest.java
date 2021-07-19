package de.thbingenproj.backend.repository;


import de.thbingenproj.backend.entity.Prices;
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

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
public class PriceRepositoryTest {
    @Autowired
    PricesRepository pricesRepository;

    @Test
    void testGeneration() {
        Assertions.assertNotNull(pricesRepository);
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
        pricesRepository.saveAll(entities);
        var repositoryTypeInRepository = pricesRepository.findAll();
        for (var type : repositoryTypeInRepository) {
            Assertions.assertTrue(entities.contains(type));
        }
    }
    @Transactional
    @Test
    void readResourceType() {
        var prices = new Prices();
        prices.setId("testID");
        pricesRepository.save(prices);
        Assertions.assertNotNull(pricesRepository.findById(prices.getId()));
    }
    @Transactional
    @Test
    void updateResourceType() {
        var resourceType = new Prices();
        resourceType.setResourceSize("500MB");
        var orgInitialName = resourceType.getResourceSize();
        resourceType.setId("testID");
        pricesRepository.save(resourceType);
        Assertions.assertNotNull(pricesRepository.findById(resourceType.getId()));
        var orgUserReturned = pricesRepository.findById(resourceType.getId()).orElse(null);
        orgUserReturned.setResourceSize("10000MB");
        pricesRepository.save(orgUserReturned);
        assertNotEquals(orgInitialName, pricesRepository.findById(resourceType.getId()).orElse(null).getResourceSize());
    }
    @Transactional
    @Test
    void findAllPricesByVdcIDTest() {
        String priceID= RandomStringUtils.randomAlphanumeric(10);
        var entities = new ArrayList<Prices>();
        for(int i = 0; i < 20; i++){
            var prices = new Prices();
            prices.setId(RandomStringUtils.randomAlphanumeric(7));
            prices.setVdcID(priceID);
            entities.add(prices);
        }  for(int i = 0; i < 50; i++){
            var prices = new Prices();
            prices.setId(RandomStringUtils.randomAlphanumeric(10));
            prices.setVdcID(RandomStringUtils.randomAlphanumeric(7));
            entities.add(prices);
        }
       pricesRepository.saveAll(entities);
        var allByVdcID=pricesRepository.findAllByVdcID(priceID);
        Assertions.assertEquals(20, allByVdcID.size());

    }
    @Transactional
    @Test
    void findAllPricesByOrgIDTest() {
        String orgID= RandomStringUtils.randomAlphanumeric(10);
        var entities = new ArrayList<Prices>();
        for(int i = 0; i < 20; i++){
            var prices = new Prices();
            prices.setId(RandomStringUtils.randomAlphanumeric(7));
            prices.setOrgID(orgID);
            entities.add(prices);
        }  for(int i = 0; i < 50; i++){
            var prices = new Prices();
            prices.setId(RandomStringUtils.randomAlphanumeric(10));
            prices.setOrgID(RandomStringUtils.randomAlphanumeric(7));
            entities.add(prices);
        }
        pricesRepository.saveAll(entities);
        var allByVdcID=pricesRepository.findAllByOrgID(orgID);
        Assertions.assertEquals(20, allByVdcID.size());

    }
    @Transactional
    @Test
    void findAllPricesByResourceIDTest() {
        String resourceID= RandomStringUtils.randomAlphanumeric(10);
        var entities = new ArrayList<Prices>();
        for(int i = 0; i < 20; i++){
            var prices = new Prices();
            prices.setId(RandomStringUtils.randomAlphanumeric(7));
            prices.setResourceID(resourceID);
            entities.add(prices);
        }  for(int i = 0; i < 50; i++){
            var prices = new Prices();
            prices.setId(RandomStringUtils.randomAlphanumeric(10));
            prices.setOrgID(RandomStringUtils.randomAlphanumeric(7));
            entities.add(prices);
        }
        pricesRepository.saveAll(entities);
        var allByResourceID=pricesRepository.findAllByResourceID(resourceID);
        Assertions.assertEquals(20, allByResourceID.size());

    }

}
