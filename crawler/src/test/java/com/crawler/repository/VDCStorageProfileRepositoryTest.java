package com.crawler.repository;

import com.crawler.entity.VDCStorageProfile;
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
class VDCStorageProfileRepositoryTest {

    @Autowired
    VDCStorageProfileRepository storageProfileRepository;

    @Test
    void testGeneration(){
        Assertions.assertNotNull(storageProfileRepository);
    }

    @Test
    @Transactional
    void createVDCStorageProfile(){
        List<VDCStorageProfile> entities = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            var vdcStorageProfile = new VDCStorageProfile();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            vdcStorageProfile.setId(generatedString);
            entities.add(vdcStorageProfile);
        }
        storageProfileRepository.saveAll(entities);
        var checkList = storageProfileRepository.findAll();
        for(VDCStorageProfile x : entities){
            Assertions.assertTrue(checkList.contains(x));
        }
    }

    @Test
    @Transactional
    void readVDCStorageProfile(){
        var vdcStorageProfile = new VDCStorageProfile();
        vdcStorageProfile.setId("testID");
        storageProfileRepository.save(vdcStorageProfile);
        Assertions.assertNotNull(storageProfileRepository.findById(vdcStorageProfile.getId()));
    }

    @Test
    @Transactional
    void deleteVDCStorageProfile(){
        var vdcStorageProfile = new VDCStorageProfile();
        vdcStorageProfile.setId("testID");
        storageProfileRepository.save(vdcStorageProfile);
        Assertions.assertNotNull(storageProfileRepository.findById(vdcStorageProfile.getId()));
        storageProfileRepository.delete(vdcStorageProfile);
        Assertions.assertNull(storageProfileRepository.findById(vdcStorageProfile.getId()).orElse(null));
    }

    @Test
    @Transactional
    void updateVDCStorageProfile(){
        var vdcStorageProfile = new VDCStorageProfile();
        vdcStorageProfile.setName("testName");
        var vdcStorageProfileInitialName = vdcStorageProfile.getName();
        vdcStorageProfile.setId("testID");
        storageProfileRepository.save(vdcStorageProfile);
        Assertions.assertNotNull(storageProfileRepository.findById(vdcStorageProfile.getId()));
        var vdcStorageProfileReturned = storageProfileRepository.findById(vdcStorageProfile.getId()).orElse(null);
        String generatedString = RandomStringUtils.randomAlphanumeric(7);
        vdcStorageProfileReturned.setName(generatedString);
        storageProfileRepository.save(vdcStorageProfileReturned);
        assertNotEquals(vdcStorageProfileInitialName, storageProfileRepository.findById(vdcStorageProfile.getId()).orElse(null).getName());
    }

    @Test
    @Transactional
    void findAllVDCStorageProfiles() {
        var entities = new ArrayList<VDCStorageProfile>();
        for(int i = 0; i < 50; i++){
            var vdcStorageProfile = new VDCStorageProfile();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            vdcStorageProfile.setId(generatedString);
            entities.add(vdcStorageProfile);
        }
        storageProfileRepository.saveAll(entities);
        var list= storageProfileRepository.findAll();
        for(var x : entities){
            Assertions.assertTrue(list.contains(x));
        }
    }
}