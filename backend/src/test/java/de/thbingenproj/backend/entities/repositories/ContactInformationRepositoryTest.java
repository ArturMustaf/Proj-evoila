package de.thbingenproj.backend.entities.repositories;

import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.user_information.ContactInformationMockCreator;
import de.thbingenproj.backend.entities.user.contact_information.ContactInformation;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
class ContactInformationRepositoryTest {
    @Autowired
    ContactInformationMockCreator contactInformationMockCreator;
    @Autowired
    ContactInformationRepository contactInformationRepository;

    @Test
    void testCreation(){
        Assert.assertNotNull(contactInformationRepository);
    }

    @Test
    @Transactional
    void createContactInformation(){
        List<ContactInformation> entities = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            var contactInformation = contactInformationMockCreator.createContactInformation();
            entities.add(contactInformation);
        }
        contactInformationRepository.saveAll(entities);
        var checkList = contactInformationRepository.findAll();
        for(ContactInformation x : entities){
            Assert.assertTrue(checkList.contains(x));
        }
    }

    @Test
    @Transactional
    void readContactInformation(){
        var contactInformation = contactInformationMockCreator.createContactInformation();
        contactInformationRepository.save(contactInformation);
        Assert.assertNotNull(contactInformationRepository.findById(contactInformation.getId()));
    }

    @Test
    @Transactional
    void deleteContactInformation(){
        var contactInformation = contactInformationMockCreator.createContactInformation();
        contactInformationRepository.save(contactInformation);
        Assert.assertNotNull(contactInformationRepository.findById(contactInformation.getId()));
        contactInformationRepository.delete(contactInformation);
        Assert.assertNull(contactInformationRepository.findById(contactInformation.getId()).orElse(null));
    }

    @Test
    @Transactional
    void updateContactInformation(){
        var contactInformation = contactInformationMockCreator.createContactInformation();
        var contactInitialMail = contactInformation.getEmail();
        contactInformationRepository.save(contactInformation);
        Assert.assertNotNull(contactInformationRepository.findById(contactInformation.getId()));
        var contactInformationReturned = contactInformationRepository.findById(contactInformation.getId()).orElse(null);
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        var generatedString = new String(array, Charset.forName("UTF-8"));
        contactInformationReturned.setEmail(generatedString);
        contactInformationRepository.save(contactInformationReturned);
        assertNotEquals(contactInitialMail, contactInformation.getEmail());
    }

}