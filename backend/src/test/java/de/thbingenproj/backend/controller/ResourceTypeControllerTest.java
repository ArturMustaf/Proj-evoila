package de.thbingenproj.backend.controller;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.logging_resources.ResourceTypeMockCreator;
import de.thbingenproj.backend.entities.ressources.ResourceType;
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
class ResourceTypeControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ResourceTypeController resourceTypeController;

    @Test
    void testCreation() {
        Assert.assertNotNull(resourceTypeController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getResourceType() throws Exception {
        mockMvc.perform(get("/create/resourceType/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void putResourceType() throws Exception{
        var resourceType = new ResourceType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(put("/create/resourceType/0")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postResourceType() throws Exception{
        var resourceType = new ResourceType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/create/resourceType")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getResourceTypeWithWrongRole() throws Exception {
        mockMvc.perform(get("/create/resourceType/0"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void putResourceTypeWithWrongRole() throws Exception{
        var resourceType = new ResourceType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(put("/create/resourceType/0")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void postResourceTypeWithWrongRole() throws Exception{
        var resourceType = new ResourceType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/create/resourceType")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getResourceTypeWithNoAuth() throws Exception {
        mockMvc.perform(get("/create/resourceType/0"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void putResourceTypeWithNoAuth() throws Exception{
        var resourceType = new ResourceType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(put("/create/resourceType/0")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void postResourceTypeWithNoAuth() throws Exception{
        var resourceType = new ResourceType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/create/resourceType")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}