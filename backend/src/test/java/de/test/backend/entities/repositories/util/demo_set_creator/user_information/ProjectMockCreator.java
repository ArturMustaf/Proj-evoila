package de.test.backend.entities.repositories.util.demo_set_creator.user_information;

import de.test.backend.entities.prices.Project;
import de.test.backend.entities.repositories.util.demo_set_creator.dataset.DataCreator;
import de.test.backend.entities.repositories.util.demo_set_creator.dataset.DataTypes;
import de.test.backend.entities.repositories.util.demo_set_creator.logging_resources.RessourceLogMockCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
/**
 * Mock creator of projects
 */
public class ProjectMockCreator {
    @Autowired
    DataCreator dataCreator;
    @Autowired
    RessourceLogMockCreator ressourceLogMockCreator;

    /**
     * Creates a Project
     *
     * @return
     */
    public Project createProject() {
        Project project = new Project();
        project.setName(dataCreator.getData(DataTypes.COMPANY_NAME) + " Projekt");
        project.setBills(new ArrayList<>());
        project.setDescription("Sample Description");
        project.setEvents(new ArrayList<>());
        ressourceLogMockCreator.createRessourceLog().forEach(project::addLog);

        return project;
    }
}
