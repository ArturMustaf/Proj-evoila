package de.thbingenproj.backend.entities.repositories;

import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.user_information.ContactInformationMockCreator;
import de.thbingenproj.backend.entities.user.contact_information.ContactInformation;
import de.thbingenproj.backend.extensions.BeforeEvery;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
class ContactInformationRepositoryTest {
    @Autowired
    ContactInformationMockCreator contactInformationMockCreator;
    @Autowired
    ContactInformationRepository repository;

    @Test
    @Transactional
    void createContactInformation(){
        List<ContactInformation> entities = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            var contactInformation = contactInformationMockCreator.createContactInformation();
            entities.add(contactInformation);
        }
        repository.saveAll(entities);
        var checkList = repository.findAll();
        for(ContactInformation x : entities){
            Assert.assertTrue(checkList.contains(x));
        }
    }

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
    }

}