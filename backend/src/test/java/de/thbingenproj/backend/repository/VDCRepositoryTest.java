package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.VDC;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
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
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
            vdc.setId(generatedString);
            entities.add(vdc);
        }
        vdcRepository.saveAll(entities);
        var vdcsInRepository= vdcRepository.findAll();
        for(var vdc : vdcsInRepository){
            Assertions.assertTrue(entities.contains(vdc));
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
    void updateVDC(){
        var vdc = new VDC();
        vdc.setName("testName");
        var vdcInitialName = vdc.getName();
        vdc.setId("testID");
        vdcRepository.save(vdc);
        Assertions.assertNotNull(vdcRepository.findById(vdc.getId()));
        var vdcReturned = vdcRepository.findById(vdc.getId()).orElse(null);
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        vdcReturned.setName(generatedString);
        vdcRepository.save(vdcReturned);
        assertNotEquals(vdcInitialName, vdcRepository.findById(vdc.getId()).orElse(null).getName());
    }

    //ToDo find all by org ID
    @Test
    @Transactional
    void findAllVDCsBy() {
        var entities = new ArrayList<VDC>();
        for(int i = 0; i < 50; i++){
            var vdc = new VDC();
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
            vdc.setId(generatedString);
            entities.add(vdc);
        }
        vdcRepository.saveAll(entities);
        var vdcsInRepository= vdcRepository.findAll();
        for(var vdc : vdcsInRepository){
            Assertions.assertTrue(entities.contains(vdc));
        }
    }
}