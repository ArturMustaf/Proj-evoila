package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entity.VDCStorageProfile;
import de.thbingenproj.backend.extensions.BeforeEvery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
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
        var vdcStorageProfilesInRepository= storageProfileRepository.findAll();
        for(var vdcStorageProfile : vdcStorageProfilesInRepository){
            Assertions.assertTrue(entities.contains(vdcStorageProfile));
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
        assertNotEquals(vdcStorageProfileInitialName, storageProfileRepository.findById(vdcStorageProfile.getId()).orElse(null).getName());
    }

    //ToDo findAllVDCStorageProfilesByVdcID
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
        var vdcStorageProfilesInRepository= storageProfileRepository.findAll();
        for(var vdcStorageProfile : vdcStorageProfilesInRepository){
            Assertions.assertTrue(entities.contains(vdcStorageProfile));
        }
    }
}