package de.thbingenproj.backend.services;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import de.thbingenproj.backend.unused.controller.CustomerController;
import de.thbingenproj.backend.unused.entities.repositories.CustomerRepository;
import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.user_information.CustomerMockCreator;
import de.thbingenproj.backend.extensions.BeforeEvery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
class SearchServiceTest {
    @Autowired
    CustomerMockCreator customerMockCreator;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    SearchService searchService;
    @Autowired
    IndexingService indexingService;
    @Autowired
    CustomerController customerController;
    @Autowired
    MockMvc mockMvc;


    //TODO implement or fix or delete
    @Test
    @Transactional
    @WithMockKeycloakAuth("ROLE_admin")
    void getUserOnQuery() {

    }

    @Test
    void getProjects() {
    }

    @Test
    void getContactInformation() {
    }

    @Test
    void getResourceType() {
    }
}