package de.thbingenproj.backend.entities.repositories;

import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.user_information.CustomerMockCreator;
import de.thbingenproj.backend.entities.user.Customer;
import de.thbingenproj.backend.extensions.BeforeEvery;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
    CustomerRepository customerRepository;
    @Autowired
    CustomerMockCreator customerMockCreator;

    @Test
    void testCreation(){
        Assert.assertNotNull(customerRepository);
    }

    @Test
    @Transactional
    void createCustomer(){
        List<Customer> entities = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            var customer = customerMockCreator.createUser();
            entities.add(customer);
        }
        customerRepository.saveAll(entities);
        var checkList = customerRepository.findAll();
        for(Customer x : entities){
            Assert.assertTrue(checkList.contains(x));
        }
    }

    @Test
    @Transactional
    void readCustomer(){
        var customer = customerMockCreator.createUser();
        customerRepository.save(customer);
        Assert.assertNotNull(customerRepository.findById(customer.getId()));
    }

    @Test
    @Transactional
    void deleteCustomer(){
        var customer = customerMockCreator.createUser();
        customerRepository.save(customer);
        Assert.assertNotNull(customerRepository.findById(customer.getId()));
        customerRepository.delete(customer);
        Assert.assertNull(customerRepository.findById(customer.getId()).orElse(null));
    }

    @Test
    @Transactional
    void updateCustomer(){
        var customer = customerMockCreator.createUser();
        var contactInitialID = customer.getUserId();
        customerRepository.save(customer);
        Assert.assertNotNull(customerRepository.findById(customer.getId()));
        var customerReturned = customerRepository.findById(customer.getId()).orElse(null);
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        var generatedString = new String(array, Charset.forName("UTF-8"));
        customerReturned.setUserId(generatedString);
        customerRepository.save(customerReturned);
        assertNotEquals(contactInitialID, customer.getUserId());
    }

    @Test
    @Transactional
    void findAllByContactInformationId() {
        var customer = customerMockCreator.createUser();
        var contact = customer.getContactInformation();
        customerRepository.save(customer);
        var result = customerRepository.findAllByContactInformationId(contact.getId());
        Assert.assertTrue(result.contains(customer));
    }

    @Test
    @Transactional
    void findAll() {
        var entities = new ArrayList<Customer>();
        for(int i = 0; i < 50; i++){
            entities.add(customerMockCreator.createUser());
        }
        customerRepository.saveAll(entities);
        var list= customerRepository.findAll();
        for(var x : entities){
            Assert.assertTrue(list.contains(x));
        }
    }

    @Test
    @Transactional
    void findCustomerByUserId() {
        var customer = customerMockCreator.createUser();
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        customer.setUserId(generatedString);
        customerRepository.save(customer);
        Assert.assertNotNull(customerRepository.findCustomerByUserId(generatedString));
    }
}
