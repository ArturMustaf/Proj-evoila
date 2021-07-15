package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entity.VApp;
import de.thbingenproj.backend.extensions.BeforeEvery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
class VAppRepositoryTest {

    @Autowired
    VAppRepository vAppRepository;

    @Test
    void testGeneration() {
        Assertions.assertNotNull(vAppRepository);
    }

    @Test
    @Transactional
    void createVApp() {
        List<VApp> entities = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            var vApp = new VApp();
            vApp.setId("r-!./dÄö" + i);
            entities.add(vApp);
        }
        vAppRepository.saveAll(entities);
        var vAppsInRepository = vAppRepository.findAll();
        for (var vApp : vAppsInRepository) {
            Assertions.assertTrue(entities.contains(vApp));
        }
    }

    @Test
    @Transactional
    void readVApp() {
        var vApp = new VApp();
        vApp.setId("testID");
        vAppRepository.save(vApp);
        Assertions.assertNotNull(vAppRepository.findById(vApp.getId()));
    }

    @Test
    @Transactional
    void deleteVApp() {
        var vApp = new VApp();
        vApp.setId("testID");
        vAppRepository.save(vApp);
        Assertions.assertNotNull(vAppRepository.findById(vApp.getId()));
        vAppRepository.delete(vApp);
        Assertions.assertNull(vAppRepository.findById(vApp.getId()).orElse(null));
    }

    @Test
    @Transactional
    void updateVApp() {
        var vApp = new VApp();
        vApp.setName("testName");
        var vAppInitialName = vApp.getName();
        vApp.setId("testID");
        vAppRepository.save(vApp);
        Assertions.assertNotNull(vAppRepository.findById(vApp.getId()));
        var vAppReturned = vAppRepository.findById(vApp.getId()).orElse(null);
        vAppReturned.setName("d.-34ge");
        vAppRepository.save(vAppReturned);
        assertNotEquals(vAppInitialName, vAppRepository.findById(vApp.getId()).orElse(null).getName());
    }



    @Test
    @Transactional
    void findAllVAppsByVdcIDy() {
        var entities = new ArrayList<VApp>();
        var vdcID = RandomStringUtils.randomAlphanumeric(10);
        for (int i = 0; i < 30; i++) {
            var vApp = new VApp();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            vApp.setId(generatedString);
            vApp.setVdcID(vdcID);
            entities.add(vApp);
        }
        for (int i = 0; i < 20; i++) {
            var vApp = new VApp();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            vApp.setId(generatedString);
            vApp.setVdcID(RandomStringUtils.randomAlphanumeric(10));
            entities.add(vApp);
        }
        vAppRepository.saveAll(entities);
        var vAppsInRepository = vAppRepository.findAllByVdcID(vdcID);

        Assertions.assertEquals(30, vAppsInRepository.size());

    }

    @Test
    @Transactional
    void findAllVApps() {
        var entities = new ArrayList<VApp>();
        for (int i = 0; i < 50; i++) {
            var vApp = new VApp();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            vApp.setId(generatedString);
            entities.add(vApp);
        }
        vAppRepository.saveAll(entities);
        var vAppsInRepository = vAppRepository.findAll();
        for (var vApp : vAppsInRepository) {
            Assertions.assertTrue(entities.contains(vApp));
        }
    }
}