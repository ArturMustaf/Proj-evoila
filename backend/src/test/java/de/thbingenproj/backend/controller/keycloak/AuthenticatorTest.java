package de.thbingenproj.backend.controller.keycloak;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
class AuthenticatorTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    Authenticator authenticator;
    @Autowired
    CustomerMockCreator customerMockCreator;

    @Test
    void testCreation(){
        Assert.assertNotNull(authenticator);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void updateUserData() throws Exception{
        var user = customerMockCreator.createUser();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(user);
        mockMvc.perform(post("/auth/update")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getCustomer() throws Exception {
        mockMvc.perform(get("/auth/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void updateUserDataWithWrongRole() throws Exception{
        var user = customerMockCreator.createUser();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(user);
        mockMvc.perform(post("/auth/update")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getCustomerWithWrongRole() throws Exception {
        mockMvc.perform(get("/auth/user"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void updateUserDataWithNoAuth() throws Exception{
        var user = customerMockCreator.createUser();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(user);
        mockMvc.perform(post("/auth/update")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getCustomerWithNoAuth() throws Exception {
        mockMvc.perform(get("/auth/user"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}