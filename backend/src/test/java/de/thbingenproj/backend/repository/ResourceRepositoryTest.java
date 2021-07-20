package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.controller.resources.ResourceController;
import de.thbingenproj.backend.entity.Prices;
import de.thbingenproj.backend.entity.Resource;
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

@Transactional
@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
public class ResourceRepositoryTest {
    @Autowired
    ResourceRepository resourceRepository;


    @Transactional
    @Test
    void testGeneration() {
        Assertions.assertNotNull(resourceRepository);
    }

    @Transactional
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
        for (var res : ResourcesInRepository) {
            Assertions.assertTrue(entities.contains(res));
        }
    }

    @Test
    @Transactional
    void readResourceType() {
        var resource = new Resource();
        resource.setId("testID");
        resourceRepository.save(resource);
        Assertions.assertNotNull(resourceRepository.findById(resource.getId()));
    }

    @Test
    @Transactional
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

    @Transactional
    @Test
    void findAllResourcesByVdcIDTest() {
        String vdcID = RandomStringUtils.randomAlphanumeric(10);
        var entities = new ArrayList<Resource>();
        for (int i = 0; i < 20; i++) {
            var resource = new Resource();
            resource.setId(RandomStringUtils.randomAlphanumeric(7));
            resource.setVdcID(vdcID);
            entities.add(resource);
        }
        for (int i = 0; i < 50; i++) {
            var resource = new Resource();
            resource.setId(RandomStringUtils.randomAlphanumeric(10));
            resource.setVdcID(RandomStringUtils.randomAlphanumeric(7));
            entities.add(resource);
        }
        resourceRepository.saveAll(entities);
        var allByVdcID = resourceRepository.findAllByVdcID(vdcID);
        Assertions.assertEquals(20, allByVdcID.size());

    }

    @Transactional
    @Test
    void findAllPricesByOrgIDTest() {
        String orgID = RandomStringUtils.randomAlphanumeric(10);
        var entities = new ArrayList<Resource>();
        for (int i = 0; i < 20; i++) {
            var resource = new Resource();
            resource.setId(RandomStringUtils.randomAlphanumeric(7));
            resource.setOrgID(orgID);
            entities.add(resource);
        }
        for (int i = 0; i < 50; i++) {
            var resource = new Resource();
            resource.setId(RandomStringUtils.randomAlphanumeric(10));
            resource.setOrgID(RandomStringUtils.randomAlphanumeric(7));
            entities.add(resource);
        }
        resourceRepository.saveAll(entities);
        var allByOrgID = resourceRepository.findAllByOrgID(orgID);
        Assertions.assertEquals(20, allByOrgID.size());

    }
@Transactional
@Test
    public void findAllByVdcIDAndStartDateIsBetweenOrEndDateIsBetweenOrEndDateBeforeAndEndDateIsNullTest() {
        var entities = new ArrayList<Resource>();
        long time=System.currentTimeMillis();
        String vdcID=RandomStringUtils.randomAlphanumeric(7);
        for (int i = 0; i < 20; i++) {
            var resource = new Resource();
            resource.setId(RandomStringUtils.randomAlphanumeric(7));
            resource.setVdcID(vdcID);
            resource.setStartDate(time - 5000);
            resource.setEndDate(time + 5000);

            entities.add(resource);
        }
        for (int i = 0; i < 10; i++) {
            var resource = new Resource();
            resource.setId(RandomStringUtils.randomAlphanumeric(7));
            resource.setVdcID(vdcID);
            resource.setId(RandomStringUtils.randomAlphanumeric(10));
            resource.setStartDate(time);
            entities.add(resource);
        }
        for (int i = 0; i < 10; i++) {
            var resource = new Resource();
            resource.setId(RandomStringUtils.randomAlphanumeric(10));
            resource.setId(RandomStringUtils.randomAlphanumeric(7));
            resource.setVdcID(vdcID);
            resource.setStartDate(time);
            resource.setEndDate(time + 2);
            entities.add(resource);
        }
    for (int i = 0; i < 10; i++) {
        var resource = new Resource();
        resource.setId(RandomStringUtils.randomAlphanumeric(7));
        resource.setVdcID(vdcID);
        resource.setStartDate(time - 5000);
        resource.setEndDate(time - 2000);

        entities.add(resource);
    }
    for (int i = 0; i < 10; i++) {
        var resource = new Resource();
        resource.setId(RandomStringUtils.randomAlphanumeric(7));
        resource.setVdcID(vdcID);
        resource.setStartDate(time + 3000);
        resource.setEndDate(time + 20000);

        entities.add(resource);
    }
    //random vdcID
    for (int i = 0; i < 10; i++) {
        var resource = new Resource();
        resource.setId(RandomStringUtils.randomAlphanumeric(10));
        resource.setOrgID(RandomStringUtils.random(7));
        resource.setVdcID(RandomStringUtils.random(7));
        resource.setStartDate(time -5000);
        resource.setEndDate(time +20);
        entities.add(resource);
    }
    for (int i = 0; i < 10; i++) {
        var resource = new Resource();
        resource.setId(RandomStringUtils.randomAlphanumeric(10));
        resource.setOrgID(RandomStringUtils.random(7));
        resource.setVdcID(RandomStringUtils.random(7));
        resource.setStartDate(time);
        resource.setEndDate(time + 2);
        entities.add(resource);
    }
    for (int i = 0; i < 10; i++) {
        var resource = new Resource();
        resource.setId(RandomStringUtils.randomAlphanumeric(10));
        resource.setOrgID(RandomStringUtils.random(7));
        resource.setVdcID(RandomStringUtils.random(7));
        resource.setStartDate(time+ 500);
        resource.setEndDate(time + 5000);
        entities.add(resource);
    }

            resourceRepository.saveAll(entities);
            long from=System.currentTimeMillis()-1000;
            long until= System.currentTimeMillis()+1000;
            var allByVdcID = resourceRepository.findAllByVdcIDAndStartDateIsBetweenOrEndDateIsBetweenOrEndDateBeforeAndEndDateIsNull(vdcID, from,until,from,until,until);
            for (var res: allByVdcID){
                System.out.println(res);
            }
        }
    @Transactional
    @Test
    public void findAllByOrgIDAndStartDateIsBetweenOrEndDateIsBetweenOrEndDateBeforeAndEndDateIsNullTest() {
        var entities = new ArrayList<Resource>();
        long time=System.currentTimeMillis();
        String orgID=RandomStringUtils.randomAlphanumeric(7);
        for (int i = 0; i < 10; i++) {
            var resource = new Resource();
            resource.setId(RandomStringUtils.randomAlphanumeric(7));
            resource.setOrgID(orgID);
            resource.setStartDate(time - 5000);
            resource.setEndDate(time + 5000);

            entities.add(resource);
        }
        for (int i = 0; i < 10; i++) {
            var resource = new Resource();
            resource.setId(RandomStringUtils.randomAlphanumeric(7));
            resource.setOrgID(orgID);
            resource.setId(RandomStringUtils.randomAlphanumeric(10));
            resource.setStartDate(time);
            entities.add(resource);
        }
        for (int i = 0; i < 10; i++) {
            var resource = new Resource();
            resource.setId(RandomStringUtils.randomAlphanumeric(10));
            resource.setId(RandomStringUtils.randomAlphanumeric(7));
            resource.setOrgID(orgID);
            resource.setStartDate(time);
            resource.setEndDate(time + 2);
            entities.add(resource);
        }
        for (int i = 0; i < 10; i++) {
            var resource = new Resource();
            resource.setId(RandomStringUtils.randomAlphanumeric(10));
            resource.setId(RandomStringUtils.randomAlphanumeric(7));
            resource.setOrgID(RandomStringUtils.random(7));
            resource.setStartDate(time);
            resource.setEndDate(time + 2);
            entities.add(resource);
        }
        for (int i = 0; i < 10; i++) {
            var resource = new Resource();
            resource.setId(RandomStringUtils.randomAlphanumeric(7));
            resource.setOrgID(orgID);
            resource.setStartDate(time - 5000);
            resource.setEndDate(time - 2000);

            entities.add(resource);
        }

        for (int i = 0; i < 10; i++) {
            var resource = new Resource();
            resource.setId(RandomStringUtils.randomAlphanumeric(7));
            resource.setOrgID(orgID);
            resource.setStartDate(time + 3000);
            resource.setEndDate(time + 20000);

            entities.add(resource);
        }
        //random orgID
        for (int i = 0; i < 10; i++) {
            var resource = new Resource();
            resource.setId(RandomStringUtils.randomAlphanumeric(10));
            resource.setOrgID(RandomStringUtils.random(7));
            resource.setVdcID(RandomStringUtils.random(7));
            resource.setStartDate(time -5000);
            resource.setEndDate(time +20);
            entities.add(resource);
        }
        for (int i = 0; i < 10; i++) {
            var resource = new Resource();
            resource.setId(RandomStringUtils.randomAlphanumeric(10));
            resource.setOrgID(RandomStringUtils.random(7));
            resource.setVdcID(RandomStringUtils.random(7));
            resource.setStartDate(time);
            resource.setEndDate(time + 2);
            entities.add(resource);
        }
        for (int i = 0; i < 10; i++) {
            var resource = new Resource();
            resource.setId(RandomStringUtils.randomAlphanumeric(10));
            resource.setOrgID(RandomStringUtils.random(7));
            resource.setVdcID(RandomStringUtils.random(7));
            resource.setStartDate(time+ 500);
            resource.setEndDate(time + 5000);
            entities.add(resource);
        }
        resourceRepository.saveAll(entities);
        long from=System.currentTimeMillis()-1000;
        long until= System.currentTimeMillis()+1000;
        var allByVdcID = resourceRepository.findAllByVdcIDAndStartDateIsBetweenOrEndDateIsBetweenOrEndDateBeforeAndEndDateIsNull(orgID, from,until,from,until,until);
        for (var res: allByVdcID){
            System.out.println(res);
        }
        //should be 30
    }
    }


