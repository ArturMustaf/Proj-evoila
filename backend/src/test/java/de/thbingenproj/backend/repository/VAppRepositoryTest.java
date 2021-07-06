package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.VApp;
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
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
            vApp.setId(generatedString);
            entities.add(vApp);
        }
        vAppRepository.saveAll(entities);
        var vAppsInRepository= vAppRepository.findAll();
        for(var vApp : vAppsInRepository){
            Assertions.assertTrue(entities.contains(vApp));
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
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        vAppReturned.setName(generatedString);
        vAppRepository.save(vAppReturned);
        assertNotEquals(vAppInitialName, vAppRepository.findById(vApp.getId()).orElse(null).getName());
    }

    //ToDo findAllVAppsByVdcID
    @Test
    @Transactional
    void findAllVApps() {
        var entities = new ArrayList<VApp>();
        for(int i = 0; i < 50; i++){
            var vApp = new VApp();
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
            vApp.setId(generatedString);
            entities.add(vApp);
        }
        vAppRepository.saveAll(entities);
        var vAppsInRepository= vAppRepository.findAll();
        for(var vApp : vAppsInRepository){
            Assertions.assertTrue(entities.contains(vApp));
        }
    }
}