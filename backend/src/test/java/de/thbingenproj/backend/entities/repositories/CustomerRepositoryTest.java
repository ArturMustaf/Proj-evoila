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
        for(Customer x : entities){
            Assert.assertTrue(checkList.contains(x));
        }
    }


    @Test
    void findAllByContactInformationId() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findCustomerByUserId() {
    }

    @Test
    @Transactional
    void createCustomers(){
        List<Customer> entities = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            var customer = creator.createUser();
            entities.add(customer);
        }
        repository.saveAll(entities);
        var checkList = repository.findAll();
        for (Customer x : entities){
            Assert.assertTrue(checkList.contains(x));
        }
    }
/*
    @Test
    @Transactional
    void readContactInformation(){
        var contactInformation = contactInformationMockCreator.createContactInformation();
        repository.save(contactInformation);
        Assert.assertNotNull(repository.findById(contactInformation.getId()));
    }

    @Test
    @Transactional
    void deleteContactInformation(){
        var contactInformation = contactInformationMockCreator.createContactInformation();
        repository.save(contactInformation);
        Assert.assertNotNull(repository.findById(contactInformation.getId()));
        repository.delete(contactInformation);
        Assert.assertNull(repository.findById(contactInformation.getId()).orElse(null));
    }

    @Test
    @Transactional
    void updateContactInformation(){
        var contactInformation = contactInformationMockCreator.createContactInformation();
        var contactFirstMail = contactInformation.getEmail();
        repository.save(contactInformation);
        Assert.assertNotNull(repository.findById(contactInformation.getId()));
        ContactInformation contactInformationReturned = repository.findById(contactInformation.getId()).orElse(null);
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        contactInformationReturned.setEmail(generatedString);
        repository.save(contactInformationReturned);
        assertNotEquals(contactFirstMail, contactInformation.getEmail());
    }*/
}
