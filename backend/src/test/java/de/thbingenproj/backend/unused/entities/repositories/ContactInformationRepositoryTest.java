package de.thbingenproj.backend.unused.entities.repositories;

import de.thbingenproj.backend.extensions.BeforeEvery;
import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.user_information.ContactInformationMockCreator;
import de.thbingenproj.backend.unused.entities.user.contact_information.ContactInformation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertionsions.assertNotEquals;

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
        Assertions.assertNotNull(contactInformationRepository);
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
            Assertions.assertTrue(checkList.contains(x));
        }
    }

    @Test
    @Transactional
    void readContactInformation(){
        var contactInformation = contactInformationMockCreator.createContactInformation();
        contactInformationRepository.save(contactInformation);
        Assertions.assertNotNull(contactInformationRepository.findById(contactInformation.getId()));
    }

    @Test
    @Transactional
    void deleteContactInformation(){
        var contactInformation = contactInformationMockCreator.createContactInformation();
        contactInformationRepository.save(contactInformation);
        Assertions.assertNotNull(contactInformationRepository.findById(contactInformation.getId()));
        contactInformationRepository.delete(contactInformation);
        Assertions.assertNull(contactInformationRepository.findById(contactInformation.getId()).orElse(null));
    }

    @Test
    @Transactional
    void updateContactInformation(){
        var contactInformation = contactInformationMockCreator.createContactInformation();
        var contactInitialMail = contactInformation.getEmail();
        contactInformationRepository.save(contactInformation);
        Assertions.assertNotNull(contactInformationRepository.findById(contactInformation.getId()));
        var contactInformationReturned = contactInformationRepository.findById(contactInformation.getId()).orElse(null);

        var generatedString = RandomStringUtils.randomAlphanumeric(10);

        contactInformationReturned.setEmail(generatedString);
        contactInformationRepository.save(contactInformationReturned);
        assertNotEquals(contactInitialMail, contactInformation.getEmail());
    }

}