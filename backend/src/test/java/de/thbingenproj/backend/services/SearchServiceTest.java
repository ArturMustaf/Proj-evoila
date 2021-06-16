package de.thbingenproj.backend.services;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.controller.CustomerController;
import de.thbingenproj.backend.entities.repositories.CustomerRepository;
import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.user_information.CustomerMockCreator;
import de.thbingenproj.backend.extensions.BeforeEvery;
import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void getUserOnQuery() throws Exception {
/*        var user = customerMockCreator.createUser();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(user);
        mockMvc.perform(post("/create/customers")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
        sleep(10000);
        var result = searchService.getUserOnQuery("", 0, 10);

        System.out.println("user: "+user);
        System.out.println("result: "+result.getData());
        Assert.assertTrue(result.getData().contains(user));*/
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