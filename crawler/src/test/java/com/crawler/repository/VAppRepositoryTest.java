package com.crawler.repository;

import com.crawler.entity.VApp;
import com.crawler.extensions.PostgresTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
class VAppRepositoryTest {

    @Autowired
    VAppRepository vAppRepository;

    @Test
    void testGeneration(){
        Assertions.assertNotNull(vAppRepository);
    }

    @Test
    @Transactional
    void createVApp(){
        List<VApp> entities = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            var vApp = new VApp();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            vApp.setId(generatedString);
            entities.add(vApp);
        }
        vAppRepository.saveAll(entities);
        var checkList = vAppRepository.findAll();
        for(VApp x : entities){
            Assertions.assertTrue(checkList.contains(x));
        }
    }

    @Test
    @Transactional
    void readVApp(){
        var vApp = new VApp();
        vApp.setId("testID");
        vAppRepository.save(vApp);
        Assertions.assertNotNull(vAppRepository.findById(vApp.getId()));
    }

    @Test
    @Transactional
    void deleteVApp(){
        var vApp = new VApp();
        vApp.setId("testID");
        vAppRepository.save(vApp);
        Assertions.assertNotNull(vAppRepository.findById(vApp.getId()));
        vAppRepository.delete(vApp);
        Assertions.assertNull(vAppRepository.findById(vApp.getId()).orElse(null));
    }

    @Test
    @Transactional
    void updateVApp(){
        var vApp = new VApp();
        vApp.setName("testName");
        var vAppInitialName = vApp.getName();
        vApp.setId("testID");
        vAppRepository.save(vApp);
        Assertions.assertNotNull(vAppRepository.findById(vApp.getId()));
        var vAppReturned = vAppRepository.findById(vApp.getId()).orElse(null);
        String generatedString = RandomStringUtils.randomAlphanumeric(7);
        vAppReturned.setName(generatedString);
        vAppRepository.save(vAppReturned);
        assertNotEquals(vAppInitialName, vAppRepository.findById(vApp.getId()).orElse(null).getName());
    }

    @Test
    @Transactional
    void findAllVApps() {
        var entities = new ArrayList<VApp>();
        for(int i = 0; i < 50; i++){
            var vApp = new VApp();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            vApp.setId(generatedString);
            entities.add(vApp);
        }
        vAppRepository.saveAll(entities);
        var list= vAppRepository.findAll();
        for(var x : entities){
            Assertions.assertTrue(list.contains(x));
        }
    }
}