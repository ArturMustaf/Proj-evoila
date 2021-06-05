package de.thbingenproj.backend.controller.costs;

import de.thbingenproj.backend.entities.prices.Price;
import de.thbingenproj.backend.entities.prices.Project;
import de.thbingenproj.backend.entities.prices.price.PriceType;
import de.thbingenproj.backend.entities.repositories.ProjectRepository;
import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entities.ressources.ResourceType;
import de.thbingenproj.backend.extensions.BeforeEveryTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SpringBootTest
@ExtendWith(BeforeEveryTest.class)
// Initializers define where to run the code against | this one creates against test containers

@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
class CostsControllerTest {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    CostsController costsController;

    @Test
    void priceForProject() {
        Project project = new Project();
        project.setName("Test Project");

        ResourceType resourceType = new ResourceType();
        resourceType.setType("VM");
        resourceType.setShortDescription("VM");
        resourceType.setFullDescription("VM");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -3000);
        List<Price> prices = new ArrayList<>();
        Price p1 = new Price();
        p1.setPrice(100);
        p1.setPriceType(PriceType.MINUTE_PRICE);
        p1.setCreated(calendar.getTime());
        prices.add(p1);
    }

}
