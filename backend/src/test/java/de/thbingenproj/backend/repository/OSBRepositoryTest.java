package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.OSB;
import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;

@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
class OSBRepositoryTest {

    @Autowired
    OSBRepository osbRepository;

    @Test
    void testGeneration(){
        Assertions.assertNotNull(osbRepository);
    }

    @Test
    @Transactional
    void createOSB(){
        var entities = new ArrayList<OSB>();
        for (int i = 0; i < 50; i++) {
            var osb = new OSB();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            osb.setId(generatedString);
            entities.add(osb);
        }
        osbRepository.saveAll(entities);
        var checkList = osbRepository.findAll();
        for(OSB x : entities){
            Assertions.assertTrue(checkList.contains(x));
        }
    }

    @Test
    @Transactional
    void readOSB(){
        var osb = new OSB();
        osb.setId("testID");
        osbRepository.save(osb);
        Assertions.assertNotNull(osbRepository.findById(osb.getId()));
    }

    @Test
    @Transactional
    void deleteOSB(){
        var osb = new OSB();
        osb.setId("testID");
        osbRepository.save(osb);
        Assertions.assertNotNull(osbRepository.findById(osb.getId()));
        osbRepository.delete(osb);
        Assertions.assertNull(osbRepository.findById(osb.getId()).orElse(null));
    }

    @Test
    @Transactional
    void updateOSB(){
        var osb = new OSB();
        osb.setName("testName");
        var orgInitialName = osb.getName();
        osb.setId("testID");
        osbRepository.save(osb);
        Assertions.assertNotNull(osbRepository.findById(osb.getId()));
        var osbReturned = osbRepository.findById(osb.getId()).orElse(null);
        String generatedString = RandomStringUtils.randomAlphanumeric(7);
        osbReturned.setName(generatedString);
        osbRepository.save(osbReturned);
        Assertions.assertNotEquals(orgInitialName, osbRepository.findById(osb.getId()).orElse(null).getName());
    }

    @Test
    @Transactional
    void findAllOSBs() {
        var entities = new ArrayList<OSB>();
        for(int i = 0; i < 50; i++){
            var osb = new OSB();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            osb.setId(generatedString);
            entities.add(osb);
        }
        osbRepository.saveAll(entities);
        var list= osbRepository.findAll();
        for(var x : entities){
            Assertions.assertTrue(list.contains(x));
        }
    }

}