package com.crawler.postgres.repository;

import com.crawler.extensions.PostgresTest;
import com.crawler.postgres.entity.App;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import java.util.ArrayList;

@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
class AppRepositoryTest {
    @Autowired
    AppRepository appRepository;

    @Test
    void testGeneration(){
        Assertions.assertNotNull(appRepository);
    }

    @Test
    @Transactional
    void createApp(){
        var entities = new ArrayList<App>();
        for (int i = 0; i < 50; i++) {
            var app = new App();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            app.setId(generatedString);
            entities.add(app);
        }
        appRepository.saveAll(entities);
        var checkList = appRepository.findAll();
        for(App x : entities){
            Assertions.assertTrue(checkList.contains(x));
        }
    }

    @Test
    @Transactional
    void readApp(){
        var app = new App();
        app.setId("testID");
        appRepository.save(app);
        Assertions.assertNotNull(appRepository.findById(app.getId()));
    }

    @Test
    @Transactional
    void deleteApp(){
        var app = new App();
        app.setId("testID");
        appRepository.save(app);
        Assertions.assertNotNull(appRepository.findById(app.getId()));
        appRepository.delete(app);
        Assertions.assertNull(appRepository.findById(app.getId()).orElse(null));
    }

    @Test
    @Transactional
    void updateApp(){
        var app = new App();
        app.setName("testName");
        var orgInitialName = app.getName();
        app.setId("testID");
        appRepository.save(app);
        Assertions.assertNotNull(appRepository.findById(app.getId()));
        var appReturned = appRepository.findById(app.getId()).orElse(null);
        String generatedString = RandomStringUtils.randomAlphanumeric(7);
        appReturned.setName(generatedString);
        appRepository.save(appReturned);
        Assertions.assertNotEquals(orgInitialName, appRepository.findById(app.getId()).orElse(null).getName());
    }

    @Test
    @Transactional
    void findAllApps() {
        var entities = new ArrayList<App>();
        for(int i = 0; i < 50; i++){
            var app = new App();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            app.setId(generatedString);
            entities.add(app);
        }
        appRepository.saveAll(entities);
        var list= appRepository.findAll();
        for(var x : entities){
            Assertions.assertTrue(list.contains(x));
        }
    }
}