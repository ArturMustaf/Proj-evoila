package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.Space;
import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;

@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
class SpaceRepositoryTest {
    @Autowired
    SpaceRepository spaceRepository;

    @Test
    void testGeneration(){
        Assertions.assertNotNull(spaceRepository);
    }

    @Test
    @Transactional
    void createSpace(){
        var entities = new ArrayList<Space>();
        for (int i = 0; i < 50; i++) {
            var space = new Space();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            space.setId(generatedString);
            entities.add(space);
        }
        spaceRepository.saveAll(entities);
        var checkList = spaceRepository.findAll();
        for(Space x : entities){
            Assertions.assertTrue(checkList.contains(x));
        }
    }

    @Test
    @Transactional
    void readSpace(){
        var space = new Space();
        space.setId("testID");
        spaceRepository.save(space);
        Assertions.assertNotNull(spaceRepository.findById(space.getId()));
    }

    @Test
    @Transactional
    void deleteSpace(){
        var space = new Space();
        space.setId("testID");
        spaceRepository.save(space);
        Assertions.assertNotNull(spaceRepository.findById(space.getId()));
        spaceRepository.delete(space);
        Assertions.assertNull(spaceRepository.findById(space.getId()).orElse(null));
    }

    @Test
    @Transactional
    void updateSpace(){
        var space = new Space();
        space.setName("testName");
        var orgInitialName = space.getName();
        space.setId("testID");
        spaceRepository.save(space);
        Assertions.assertNotNull(spaceRepository.findById(space.getId()));
        var spaceReturned = spaceRepository.findById(space.getId()).orElse(null);
        String generatedString = RandomStringUtils.randomAlphanumeric(7);
        spaceReturned.setName(generatedString);
        spaceRepository.save(spaceReturned);
        Assertions.assertNotEquals(orgInitialName, spaceRepository.findById(space.getId()).orElse(null).getName());
    }

    @Test
    @Transactional
    void findAllSpaces() {
        var entities = new ArrayList<Space>();
        for(int i = 0; i < 50; i++){
            var space = new Space();
            String generatedString = RandomStringUtils.randomAlphanumeric(10);
            space.setId(generatedString);
            entities.add(space);
        }
        spaceRepository.saveAll(entities);
        var list= spaceRepository.findAll();
        for(var x : entities){
            Assertions.assertTrue(list.contains(x));
        }
    }

}