package com.crawler.postgres.repository;

import com.crawler.postgres.entity.VDC;
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
class VDCRepositoryTest {
    @Autowired
    VDCRepository vdcRepository;

    @Test
    void testGeneration(){
        Assertions.assertNotNull(vdcRepository);
    }

    @Test
    @Transactional
    void createVDC(){
        List<VDC> entities = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            var vdc = new VDC();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            vdc.setId(generatedString);
            entities.add(vdc);
        }
        vdcRepository.saveAll(entities);
        var checkList = vdcRepository.findAll();
        for(VDC x : entities){
            Assertions.assertTrue(checkList.contains(x));
        }
    }

    @Test
    @Transactional
    void readVDC(){
        var vdc = new VDC();
        vdc.setId("testID");
        vdcRepository.save(vdc);
        Assertions.assertNotNull(vdcRepository.findById(vdc.getId()));
    }

    @Test
    @Transactional
    void deleteVDC(){
        var vdc = new VDC();
        vdc.setId("testID");
        vdcRepository.save(vdc);
        Assertions.assertNotNull(vdcRepository.findById(vdc.getId()));
        vdcRepository.delete(vdc);
        Assertions.assertNull(vdcRepository.findById(vdc.getId()).orElse(null));
    }

    @Test
    @Transactional
    void updateVDC(){
        var vdc = new VDC();
        vdc.setName("testName");
        var vdcInitialName = vdc.getName();
        vdc.setId("testID");
        vdcRepository.save(vdc);
        Assertions.assertNotNull(vdcRepository.findById(vdc.getId()));
        var vdcReturned = vdcRepository.findById(vdc.getId()).orElse(null);
        String generatedString = RandomStringUtils.randomAlphanumeric(7);
        vdcReturned.setName(generatedString);
        vdcRepository.save(vdcReturned);
        assertNotEquals(vdcInitialName, vdcRepository.findById(vdc.getId()).orElse(null).getName());
    }

    @Test
    @Transactional
    void findAllVDCs() {
        var entities = new ArrayList<VDC>();
        for(int i = 0; i < 50; i++){
            var vdc = new VDC();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            vdc.setId(generatedString);
            entities.add(vdc);
        }
        vdcRepository.saveAll(entities);
        var list= vdcRepository.findAll();
        for(var x : entities){
            Assertions.assertTrue(list.contains(x));
        }
    }
}