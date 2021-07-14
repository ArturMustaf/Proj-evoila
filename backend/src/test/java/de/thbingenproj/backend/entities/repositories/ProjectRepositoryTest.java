package de.thbingenproj.backend.entities.repositories;

import de.thbingenproj.backend.entities.prices.Project;
import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.logging_resources.RessourceLogMockCreator;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.user_information.ProjectMockCreator;
import de.thbingenproj.backend.extensions.BeforeEvery;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
class ProjectRepositoryTest {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectMockCreator projectMockCreator;
    @Autowired
    RessourceLogMockCreator ressourceLogMockCreator;

    @Test
    void testCreation(){
        Assert.assertNotNull(projectRepository);
    }

    @Test
    @Transactional
    void createProject(){
        List<Project> entities = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            var project = projectMockCreator.createProject();
            entities.add(project);
        }
        projectRepository.saveAll(entities);
        var checkList = projectRepository.findAll();
        for(Project x : entities){
            Assert.assertTrue(checkList.contains(x));
        }
    }

    @Test
    @Transactional
    void readProject(){
        var project = projectMockCreator.createProject();
        projectRepository.save(project);
        Assert.assertNotNull(projectRepository.findById(project.getId()));
    }

    @Test
    @Transactional
    void deleteProject(){
        var project = projectMockCreator.createProject();
        projectRepository.save(project);
        Assert.assertNotNull(projectRepository.findById(project.getId()));
        projectRepository.delete(project);
        Assert.assertNull(projectRepository.findById(project.getId()).orElse(null));
    }

    @Test
    @Transactional
    void updateProject(){
        var project = projectMockCreator.createProject();
        var projectInitialName = project.getName();
        projectRepository.save(project);
        Assert.assertNotNull(projectRepository.findById(project.getId()));
        var customerReturned = projectRepository.findById(project.getId()).orElse(null);
        String generatedString = RandomStringUtils.randomAlphanumeric(10);
        customerReturned.setName(generatedString);
        projectRepository.save(customerReturned);
        assertNotEquals(projectInitialName, project.getName());
    }

    @Test
    @Transactional
    void findAllLogs() {
        var entities = new ArrayList<Project>();
        var testEntity = projectMockCreator.createProject();
        for(int i = 0; i < 50; i++){
            entities.add(projectMockCreator.createProject());
        }
        projectRepository.saveAll(entities);
        projectRepository.save(testEntity);
        Assert.assertNotNull(projectRepository.findAllLogs(testEntity.getId()));
    }

    @Test
    @Transactional
    void findAll() {
        var entities = new ArrayList<Project>();
        for(int i = 0; i < 50; i++){
            entities.add(projectMockCreator.createProject());
        }
        projectRepository.saveAll(entities);
        var list= projectRepository.findAll();
        for(var x : entities){
            Assert.assertTrue(list.contains(x));
        }
    }
}