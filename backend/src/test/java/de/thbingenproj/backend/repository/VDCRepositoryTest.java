package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entity.VDC;
import de.thbingenproj.backend.extensions.BeforeEvery;
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


@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
class VDCRepositoryTest {
    @Autowired
    VDCRepository vdcRepository;

    @Test
    void testGeneration() {
        Assertions.assertNotNull(vdcRepository);
    }

    @Test
    @Transactional
    void createVDC() {
        List<VDC> entities = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            var vdc = new VDC();
            vdc.setId("1ÄöOtsdhjs" + i);
            entities.add(vdc);
        }
        vdcRepository.saveAll(entities);
        var vdcsInRepository = vdcRepository.findAll();
        for (var vdc : vdcsInRepository) {
            Assertions.assertTrue(entities.contains(vdc));
        }
    }

    @Test
    @Transactional
    void readVDC() {
        var vdc = new VDC();
        vdc.setId("testID");
        vdcRepository.save(vdc);
        Assertions.assertNotNull(vdcRepository.findById(vdc.getId()));
    }

    @Test
    @Transactional
    void updateVDC() {
        var vdc = new VDC();
        vdc.setName("testName");
        var vdcInitialName = vdc.getName();
        vdc.setId("testID");
        vdcRepository.save(vdc);
        Assertions.assertNotNull(vdcRepository.findById(vdc.getId()));
        var vdcReturned = vdcRepository.findById(vdc.getId()).orElse(null);

        vdcReturned.setName("1-ÄöOÄutd");
        vdcRepository.save(vdcReturned);
        assertNotEquals(vdcInitialName, vdcRepository.findById(vdc.getId()).orElse(null).getName());
    }

    @Test
    @Transactional
    void findAllVDCsByOrgID() {
        String orgID = "Test-;RÄ";
        var entities = new ArrayList<VDC>();
        for (int i = 0; i < 30; i++) {
            var vdc = new VDC();
            boolean useLetters = true;
            boolean useNumbers = true;
            String generatedString = RandomStringUtils.random(10, useLetters, useNumbers);
            vdc.setId(generatedString);
            vdc.setOrgID(orgID);
            entities.add(vdc);
        }
        for (int i = 0; i < 20; i++) {
            var vdc = new VDC();
            boolean useLetters = true;
            boolean useNumbers = true;
            String generatedString = RandomStringUtils.random(10, useLetters, useNumbers);
            vdc.setId(generatedString);
            vdc.setOrgID("f5" + i);
            entities.add(vdc);
        }
        vdcRepository.saveAll(entities);
        var vdcsInRepository = vdcRepository.findAllByOrgID(orgID);

        Assertions.assertEquals(30, vdcsInRepository.size());

    }

    @Test
    @Transactional
    void findAllVDCs() {
        var entities = new ArrayList<VDC>();
        for (int i = 0; i < 50; i++) {
            var vdc = new VDC();
            boolean useLetters = true;
            boolean useNumbers = true;
            String generatedString = RandomStringUtils.random(10, useLetters, useNumbers);
            vdc.setId(generatedString);
            entities.add(vdc);
        }
        vdcRepository.saveAll(entities);
        var vdcsInRepository = vdcRepository.findAll();
        for (var vdc : vdcsInRepository) {
            Assertions.assertTrue(entities.contains(vdc));
        }
    }
}