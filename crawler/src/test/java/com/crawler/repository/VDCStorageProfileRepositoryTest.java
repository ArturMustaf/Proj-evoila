package com.crawler.repository;

import com.crawler.entity.VDCStorageProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
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
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        vdcStorageProfileReturned.setName(generatedString);
        storageProfileRepository.save(vdcStorageProfileReturned);
        assertNotEquals(vdcStorageProfileInitialName, storageProfileRepository.findById(vdcStorageProfile.getId()));
    }

    @Test
    @Transactional
    void findAllVDCStorageProfiles() {
        var entities = new ArrayList<VDCStorageProfile>();
        for(int i = 0; i < 50; i++){
            var vdcStorageProfile = new VDCStorageProfile();
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
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