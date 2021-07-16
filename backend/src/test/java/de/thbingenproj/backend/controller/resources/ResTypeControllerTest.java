package de.thbingenproj.backend.controller.resources;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.entity.ResType;
import de.thbingenproj.backend.extensions.BeforeEvery;
import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;


import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
public class ResTypeControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ResTypeController resourceTypeController;

    @Test
    void testCreation() {
        Assert.assertNotNull(resourceTypeController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postResourceType() throws Exception {
        var resourceType = new ResType();
        resourceType.setId(RandomStringUtils.randomAlphanumeric(10));
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/create/resource-types")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void postResourceTypeWithWrongRole() throws Exception {
        var resourceType = new ResType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/create/resource-types")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getResourceType() throws Exception {
        mockMvc.perform(get("/resource-types/0"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getResourceTypeUser() throws Exception {
        mockMvc.perform(get("/resource-types/0"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }


    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getResourceTypeWithWrongRole() throws Exception {
        mockMvc.perform(get("/create/resource-types/0"))
                .andExpect(status().isForbidden());
    }


    @Test
    void getResourceTypeWithNoAuth() throws Exception {
        mockMvc.perform(get("/create/resource-types/0"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void postResourceTypeWithNoAuth() throws Exception {
        var resourceType = new ResType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/create/resourceTypes")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllResourceTypesWithWrongRole() throws Exception {
        mockMvc.perform(get("/admin/resource-types/"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllResourceTypes() throws Exception {
        mockMvc.perform(get("/admin/resource-types"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

}


