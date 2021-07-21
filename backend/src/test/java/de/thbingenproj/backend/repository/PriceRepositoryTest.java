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
import java.util.Random;

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

        pricesRepository.save(prices);
        Assertions.assertNotNull(pricesRepository.findById(prices.getId()));
    }
    @Transactional
    @Test
    void updateResourceType() {
        var resourceType = new Prices();
        resourceType.setResourceSize(RandomStringUtils.randomAlphanumeric(5));
        var orgInitialName = resourceType.getResourceSize();
        Random random=new Random();
        pricesRepository.save(resourceType);
        Assertions.assertNotNull(pricesRepository.findById(resourceType.getId()));
        var orgUserReturned = pricesRepository.findById(resourceType.getId()).orElse(null);
        orgUserReturned.setResourceSize(RandomStringUtils.randomAlphanumeric(8));
        pricesRepository.save(orgUserReturned);
        assertNotEquals(orgInitialName, pricesRepository.findById(resourceType.getId()).orElse(null).getResourceSize());
    }
    @Transactional
    @Test
    void findAllPricesByVdcIDTest() {
        String vdcID= RandomStringUtils.randomAlphanumeric(10);
        var entities = new ArrayList<Prices>();
        for(int i = 0; i < 20; i++){
            var prices = new Prices();
            prices.setVdcID(vdcID);
            entities.add(prices);
        }  for(int i = 0; i < 50; i++){
            var prices = new Prices();
            prices.setVdcID(RandomStringUtils.randomAlphanumeric(7));
            entities.add(prices);
        }
       pricesRepository.saveAll(entities);
        var allByVdcID=pricesRepository.findAllByVdcID(vdcID);
        Assertions.assertEquals(20, allByVdcID.size());

    }
    @Transactional
    @Test
    void findAllPricesByOrgIDTest() {
        String orgID= RandomStringUtils.randomAlphanumeric(10);
        var entities = new ArrayList<Prices>();
        for(int i = 0; i < 20; i++){
            var prices = new Prices();
            prices.setOrgID(orgID);
            entities.add(prices);
        }  for(int i = 0; i < 50; i++){
            var prices = new Prices();
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
        Random random=new Random();
        long resourceID= random.nextInt();
        var entities = new ArrayList<Prices>();

        for(int i = 0; i < 20; i++){
            var prices = new Prices();
            prices.setResourceID(resourceID);
            entities.add(prices);
        }  for(int i = 0; i < 50; i++){
            var prices = new Prices();
            prices.setResourceID(random.nextInt());
            prices.setOrgID(RandomStringUtils.randomAlphanumeric(7));
            entities.add(prices);
        }
        pricesRepository.saveAll(entities);
        var allByResourceID=pricesRepository.findAllByResourceID(resourceID);
        Assertions.assertEquals(20, allByResourceID.size());

    }
    @Transactional
    @Test
    public void findAllByVdcIDWithinTimeframeTest() {
        var entities = new ArrayList<Prices>();
        long time=System.currentTimeMillis();
        String vdcID=RandomStringUtils.randomAlphanumeric(7);
        for (int i = 0; i < 10; i++) {
            var prices = new Prices();
            prices.setVdcID(vdcID);
            prices.setStartDate(time - 5000);
            prices.setEndDate(time + 5000);

            entities.add(prices);
        }
        for (int i = 0; i < 10; i++) {
            var prices = new Prices();
            prices.setVdcID(vdcID);
            prices.setStartDate(time);
            entities.add(prices);
        }
        for (int i = 0; i < 10; i++) {
            var prices = new Prices();
            prices.setVdcID(vdcID);
            prices.setStartDate(time);
            prices.setEndDate(time + 2);
            entities.add(prices);
        }
        for (int i = 0; i < 10; i++) {
            var prices = new Prices();
            prices.setVdcID(vdcID);
            prices.setStartDate(time - 5000);
            prices.setEndDate(time - 2000);

            entities.add(prices);
        }
        for (int i = 0; i < 10; i++) {
            var prices = new Prices();
            prices.setVdcID(vdcID);
            prices.setStartDate(time + 3000);
            prices.setEndDate(time + 20000);

            entities.add(prices);
        }
        //random vdcID
        for (int i = 0; i < 10; i++) {
            var prices = new Prices();
            prices.setOrgID(RandomStringUtils.randomAlphanumeric(7));
            prices.setVdcID(RandomStringUtils.randomAlphanumeric(7));
            prices.setStartDate(time -5000);
            prices.setEndDate(time +20);
            entities.add(prices);
        }
        for (int i = 0; i < 10; i++) {
            var prices = new Prices();
            prices.setOrgID(RandomStringUtils.randomAlphanumeric(7));
            prices.setVdcID(RandomStringUtils.randomAlphanumeric(7));
            prices.setStartDate(time);
            prices.setEndDate(time + 2);
            entities.add(prices);
        }
        for (int i = 0; i < 10; i++) {
            var prices = new Prices();
            prices.setOrgID(RandomStringUtils.randomAlphanumeric(7));
            prices.setVdcID(RandomStringUtils.randomAlphanumeric(7));
            prices.setStartDate(time+ 500);
            prices.setEndDate(time + 5000);
            entities.add(prices);
        }

        pricesRepository.saveAll(entities);
        long from=System.currentTimeMillis()-1000;
        long until= System.currentTimeMillis()+1000;
        var allByVdcID = pricesRepository.findAllByVDCIDWithinTimeframe(vdcID, from, until);
        //should be 30
    Assertions.assertEquals(30, allByVdcID.size());
    }
    @Transactional
    @Test
    public void findAllByOrgIDWithinTimeframeTest() {
        var entities = new ArrayList<Prices>();
        long time=System.currentTimeMillis();
        String orgID=RandomStringUtils.randomAlphanumeric(7);
        for (int i = 0; i < 10; i++) {
            var prices = new Prices();
            prices.setOrgID(orgID);
            prices.setStartDate(time - 5000);
            prices.setEndDate(time + 5000);

            entities.add(prices);
        }
        for (int i = 0; i < 10; i++) {
            var prices = new Prices();
            prices.setOrgID(orgID);
            prices.setStartDate(time);
            entities.add(prices);
        }
        for (int i = 0; i < 10; i++) {
            var prices = new Prices();
            prices.setOrgID(orgID);
            prices.setStartDate(time);
            prices.setEndDate(time + 2);
            entities.add(prices);
        }

        for (int i = 0; i < 10; i++) {
            var prices = new Prices();
            prices.setOrgID(orgID);
            prices.setStartDate(time - 5000);
            prices.setEndDate(time - 2000);

            entities.add(prices);
        }

        for (int i = 0; i < 10; i++) {
            var prices = new Prices();
            prices.setOrgID(orgID);
            prices.setStartDate(time + 3000);
            prices.setEndDate(time + 20000);

            entities.add(prices);
        }

        //random orgId
        for (int i = 0; i < 10; i++) {
            var prices = new Prices();
            prices.setOrgID(RandomStringUtils.randomAlphanumeric(7));
            prices.setVdcID(RandomStringUtils.randomAlphanumeric(7));
            prices.setStartDate(time -5000);
            prices.setEndDate(time +20);
            entities.add(prices);
        }
        for (int i = 0; i < 10; i++) {
            var prices = new Prices();
            prices.setOrgID(RandomStringUtils.randomAlphanumeric(7));
            prices.setVdcID(RandomStringUtils.randomAlphanumeric(7));
            prices.setStartDate(time);
            prices.setEndDate(time + 2);
            entities.add(prices);
        }
        for (int i = 0; i < 10; i++) {
            var prices = new Prices();
            prices.setOrgID(RandomStringUtils.randomAlphanumeric(7));
            prices.setVdcID(RandomStringUtils.randomAlphanumeric(7));
            prices.setStartDate(time+ 500);
            prices.setEndDate(time + 5000);
            entities.add(prices);
        }

        pricesRepository.saveAll(entities);
        long from=System.currentTimeMillis()-1000;
        long until= System.currentTimeMillis()+1000;
        var allByOrgID = pricesRepository.findAllByOrgIDWithinTimeframe(orgID, from, until);

        //should be 30
        Assertions.assertEquals(30, allByOrgID.size());


    }

}
