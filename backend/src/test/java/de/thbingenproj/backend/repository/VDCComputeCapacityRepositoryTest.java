package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entity.VDCComputeCapacity;
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
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotEquals;


@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
class VDCComputeCapacityRepositoryTest {

    @Autowired
    VDCComputeCapacityRepository computeCapacityRepository;

    @Test
    void testGeneration(){
        Assertions.assertNotNull(computeCapacityRepository);
    }

    @Test
    @Transactional
    void createVDCComputeCapacity(){
        var entities = new ArrayList<VDCComputeCapacity>();
        for (int i = 0; i < 50; i++) {
            var vdcComputeCapacity = new VDCComputeCapacity();
            entities.add(vdcComputeCapacity);
        }
        computeCapacityRepository.saveAll(entities);
        var computeCapacitiesInRepository= computeCapacityRepository.findAll();
        for(var computeCapacity : computeCapacitiesInRepository){
            Assertions.assertTrue(entities.contains(computeCapacity));
        }
    }

    @Test
    @Transactional
    void readVDCComputeCapacity(){
        var vdcComputeCapacity = new VDCComputeCapacity();
        vdcComputeCapacity.setId(new Random().nextLong());
        computeCapacityRepository.save(vdcComputeCapacity);
        Assertions.assertNotNull(computeCapacityRepository.findById(vdcComputeCapacity.getId()));
    }

    @Test
    @Transactional
    void deleteVDCComputeCapacity(){
        var vdcComputeCapacity = new VDCComputeCapacity();
        vdcComputeCapacity.setId(new Random().nextLong());
        computeCapacityRepository.save(vdcComputeCapacity);
        Assertions.assertNotNull(computeCapacityRepository.findById(vdcComputeCapacity.getId()));
        computeCapacityRepository.delete(vdcComputeCapacity);
        Assertions.assertNull(computeCapacityRepository.findById(vdcComputeCapacity.getId()).orElse(null));
    }

    @Test
    @Transactional
    void updateVDCComputeCapacity(){
        var vdcComputeCapacity = new VDCComputeCapacity();
        vdcComputeCapacity.setCpuAllocated(new Random().nextInt());
        var vdcComputeCapacityInitialCpuAllocated = vdcComputeCapacity.getCpuAllocated();
        computeCapacityRepository.save(vdcComputeCapacity);
        Assertions.assertNotNull(computeCapacityRepository.findById(vdcComputeCapacity.getId()));
        var vdcComputeCapacityReturned = computeCapacityRepository.findById(vdcComputeCapacity.getId()).orElse(null);
        vdcComputeCapacityReturned.setCpuAllocated(new Random().nextInt());
        computeCapacityRepository.save(vdcComputeCapacityReturned);
        assertNotEquals(vdcComputeCapacityInitialCpuAllocated, computeCapacityRepository.findById(vdcComputeCapacity.getId()).orElse(null).getCpuAllocated());
    }


    @Test
    @Transactional
    void findAllVDCComputeCapacitiesByVdcID() {
        String vdcId= RandomStringUtils.randomAlphanumeric(10);
        var entities = new ArrayList<VDCComputeCapacity>();
        for(int i = 0; i < 20; i++){
            var vdcComputeCapacity = new VDCComputeCapacity();
            vdcComputeCapacity.setVdcID(vdcId);
            entities.add(vdcComputeCapacity);
        }  for(int i = 0; i < 50; i++){
            var vdcComputeCapacity = new VDCComputeCapacity();
            entities.add(vdcComputeCapacity);
        }
        computeCapacityRepository.saveAll(entities);
        var computeCapacitiesInRepository= computeCapacityRepository.findAllByVdcID(vdcId);
        Assertions.assertEquals(20, computeCapacitiesInRepository.size());

    }





    @Test
    @Transactional
    void findAllVDCComputeCapacities() {
        var entities = new ArrayList<VDCComputeCapacity>();
        for(int i = 0; i < 50; i++){
            var vdcComputeCapacity = new VDCComputeCapacity();
            entities.add(vdcComputeCapacity);
        }
        computeCapacityRepository.saveAll(entities);
        var computeCapacitiesInRepository= computeCapacityRepository.findAll();
        for(var computeCapacity : computeCapacitiesInRepository){
            Assertions.assertTrue(entities.contains(computeCapacity));
        }
    }
}