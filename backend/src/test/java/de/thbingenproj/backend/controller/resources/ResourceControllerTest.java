package de.thbingenproj.backend.controller.resources;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entity.ResType;
import de.thbingenproj.backend.entity.Resource;
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
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
public class ResourceControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ResourceController resourceController;

    @Test
    void testCreation() {
        Assert.assertNotNull(resourceController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postResourceTest() throws Exception {
        var resource = new Resource();
        resource.setId(RandomStringUtils.randomAlphanumeric(10));
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resource);
        mockMvc.perform(post("/create/resources")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void postResourceWithWrongRoleTest() throws Exception {
        var resource = new Resource();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resource);
        mockMvc.perform(post("/create/resources")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getResourceAdminTest() throws Exception {
        mockMvc.perform(get("/resources/0"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getResourceUserTest() throws Exception {
        mockMvc.perform(get("/resources/0"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllResourcesWithWrongRoleTest() throws Exception {
        mockMvc.perform(get("/admin/resources"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllResourcesAdminTest() throws Exception {
        mockMvc.perform(get("/admin/resources"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllResourcesByOrgIDUserTest() throws Exception {
        mockMvc.perform(get("/resources/organizations/0"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllResourcesByVDCIDUserTest() throws Exception {
        mockMvc.perform(get("/resources/vdcs/0"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllResourcesByOrgIDAdminTest() throws Exception {
        mockMvc.perform(get("/resources/organizations/0"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllResourcesByVDCIDAdminTest() throws Exception {
        mockMvc.perform(get("/resources/vdcs/0"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

}
