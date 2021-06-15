package de.thbingenproj.backend.controller;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.user_information.ContactInformationMockCreator;
import de.thbingenproj.backend.extensions.BeforeEvery;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
class ContactInformationControllerTest {

    @Autowired
    ContactInformationMockCreator contactInformationMockCreator;
    @Autowired
    ContactInformationController contactInformationController;
    @Autowired
    MockMvc mockMvc;

    @Test
    void testCreation() {
        Assert.assertNotNull(contactInformationController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getContactInformation() throws Exception{
        mockMvc.perform(get("/create/contactInformation/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void putContactInformation() throws Exception{
        var contactInformation = contactInformationMockCreator.createContactInformation();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(contactInformation);
        mockMvc.perform(put("/create/contactInformation/0")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postCustomer() throws Exception{
        var contactInformation = contactInformationMockCreator.createContactInformation();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(contactInformation);
        mockMvc.perform(post("/create/contactInformation")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getContactInformationWithWrongRole() throws Exception{
        mockMvc.perform(get("/create/contactInformation/0"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void putContactInformationWithWrongRole() throws Exception{
        var contactInformation = contactInformationMockCreator.createContactInformation();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(contactInformation);
        mockMvc.perform(put("/create/contactInformation/0")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void postCustomerWithWrongRole() throws Exception{
        var contactInformation = contactInformationMockCreator.createContactInformation();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(contactInformation);
        mockMvc.perform(post("/create/contactInformation")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    void getContactInformationWithNoAuth() throws Exception{
        mockMvc.perform(get("/create/contactInformation/0"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void putContactInformationWithNoAuth() throws Exception{
        var contactInformation = contactInformationMockCreator.createContactInformation();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(contactInformation);
        mockMvc.perform(put("/create/contactInformation/0")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void postCustomerWithNoAuth() throws Exception{
        var contactInformation = contactInformationMockCreator.createContactInformation();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(contactInformation);
        mockMvc.perform(post("/create/contactInformation")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}