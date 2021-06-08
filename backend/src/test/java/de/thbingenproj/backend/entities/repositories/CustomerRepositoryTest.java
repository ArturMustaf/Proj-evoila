package de.thbingenproj.backend.entities.repositories;

import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.user_information.CustomerMockCreator;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.user_information.ProjectMockCreator;
import de.thbingenproj.backend.entities.user.Customer;
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

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
//@ContextConfiguration(initializers = {LocalInitalizer.PropertiesInitializer.class})
/**
 * This a test for checking if the datacreation is successful
 * in development its useful to switch the @ContextConfiguration to the commented LocalInitializer since this is able
 * to write persistently in the development database
 * The @ExtendWith is the initializer for creating a testcontainer with elasticsearch
 * In general all tests should be run against the instances which are provided through the testcontainer instances.
 */
class CustomerRepositoryTest extends PostgresTest {

    @Autowired
    CustomerRepository repository;
    @Autowired
    CustomerMockCreator creator;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectMockCreator projectCreator;


    @Test
    @Transactional
    void testCreation() {
        List<Customer> entities = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            Customer customer = creator.createUser();
            entities.add(customer);
        }
        repository.saveAll(entities);
        var checkList = repository.findAll();
        Assert.assertEquals(entities, checkList);
    }


}
