package de.thbingenproj.backend.controller;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.user_information.CustomerMockCreator;
import de.thbingenproj.backend.extensions.BeforeEvery;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private CustomerController customerController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CustomerMockCreator customerMockCreator;

    @Test
    void testCreation() {
        Assert.assertNotNull(customerController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void putCustomer() throws Exception{
        var user = customerMockCreator.createUser();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(user);
        mockMvc.perform(put("/create/customers/0")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postCustomer() throws Exception {
        var user = customerMockCreator.createUser();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(user);
        mockMvc.perform(post("/create/customers")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void isLinked() throws Exception {
        mockMvc.perform(get("/create/customers/linked/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getCustomer() throws Exception{
        mockMvc.perform(get("/create/customers/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void putCustomerWithWrongRole() throws Exception{
        var user = customerMockCreator.createUser();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(user);
        mockMvc.perform(put("/create/customers/0")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void postCustomerWithWrongRole() throws Exception {
        var user = customerMockCreator.createUser();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(user);
        mockMvc.perform(post("/create/customers")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void isLinkedWithWrongRole() throws Exception {
        mockMvc.perform(get("/create/customers/linked/0"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getCustomerWithWrongRole() throws Exception{
        mockMvc.perform(get("/create/customers/0"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void putCustomerWithNoAuth() throws Exception{
        var user = customerMockCreator.createUser();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(user);
        mockMvc.perform(put("/create/customers/0")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void postCustomerWithNoAuth() throws Exception {
        var user = customerMockCreator.createUser();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(user);
        mockMvc.perform(post("/create/customers")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void isLinkedWithNoAuth() throws Exception {
        mockMvc.perform(get("/create/customers/linked/0"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getCustomerWithNoAuth() throws Exception{
        mockMvc.perform(get("/create/customers/0"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}