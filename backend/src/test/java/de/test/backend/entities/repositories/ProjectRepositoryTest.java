package de.test.backend.entities.repositories;

import de.test.backend.entities.prices.Project;
import de.test.backend.entities.repositories.container.LocalInitalizer;
import de.test.backend.entities.repositories.container.PostgresTest;
import de.test.backend.entities.repositories.util.demo_set_creator.logging_resources.RessourceLogMockCreator;
import de.test.backend.entities.repositories.util.demo_set_creator.user_information.ProjectMockCreator;
import de.test.backend.events.CreationListener;
import de.test.backend.extensions.BeforeEveryTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
//@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@ExtendWith(BeforeEveryTest.class)
@ContextConfiguration(initializers = {LocalInitalizer.PropertiesInitializer.class})
/**
 * This a test for checking if the datacreation is successful
 * in development its useful to switch the @ContextConfiguration to the commented LocalInitializer since this is able
 * to write persistently in the development database
 * The @ExtendWith is the initializer for creating a testcontainer with elasticsearch
 * In general all tests should be run against the instances which are provided through the testcontainer instances.
 */
public class ProjectRepositoryTest extends PostgresTest {

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
    void createProjects() throws InterruptedException {
        System.out.println("Zeile 1");
        List<Project> entities = new ArrayList<>();
        System.out.println("Test");
        for (int i = 0; i < 50; i++) {
            Project project = creator.createProject();
            entities.add(project);
        }
        for (Project project : entities)
            repository.save(project);
        creationListener.createRuntimePairs();
        Thread.sleep(120000);

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
