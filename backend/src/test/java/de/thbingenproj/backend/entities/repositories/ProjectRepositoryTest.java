package de.thbingenproj.backend.entities.repositories;

import de.thbingenproj.backend.entities.prices.Project;
import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.logging_resources.RessourceLogMockCreator;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.user_information.ProjectMockCreator;
import de.thbingenproj.backend.events.CreationListener;
import de.thbingenproj.backend.extensions.BeforeEvery;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@ExtendWith(BeforeEvery.class)
//@ContextConfiguration(initializers = {LocalInitalizer.PropertiesInitializer.class})
/**
 * This a test for checking if the datacreation is successful
 * in development its useful to switch the @ContextConfiguration to the commented LocalInitializer since this is able
 * to write persistently in the development database
 * The @ExtendWith is the initializer for creating a testcontainer with elasticsearch
 * In general all tests should be run against the instances which are provided through the testcontainer instances.
 */
class ProjectRepositoryTest extends PostgresTest {

    @Autowired
    ProjectRepository repository;
    @Autowired
    ProjectMockCreator creator;
    @Autowired
    RessourceLogMockCreator ressourceLogMockCreator;
    @Autowired
    RuntimePairRepository runtimePairRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    CreationListener creationListener;

    @Test
    @Transactional
    void createProjects() throws InterruptedException {
        List<Project> entities = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Project project = creator.createProject();
            entities.add(project);
        }
        for (Project project : entities)
            repository.save(project);
        var checkList = repository.findAll();

        for(Project x : entities){
            Assert.assertTrue(checkList.contains(x));
        }
        //Assert.assertTrue(checkList.contains(entities));
        //Assert.assertEquals(entities, checkList);
        //creationListener.createRuntimePairs();
        //todo ?;

    }

    @Test
    void findAllLogs() {
    }

    @Test
    void findAll() {
    }

//    @Test
//    void createResourceTypes(){
//        List<ResourceLog> entities = new ArrayList<>();
//        for (int i = 0; i < 50; i++) {
//            ResourceLog entity = ressourceLogMockCreator.createRessourceLog();
//            entities.add(entity);
//        }
//        repository.saveAll(entities);
//    }

}
