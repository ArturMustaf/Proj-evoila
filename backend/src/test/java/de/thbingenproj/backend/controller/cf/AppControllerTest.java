package de.thbingenproj.backend.controller.cf;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.entity.App;
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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
class AppControllerTest {
    @Autowired
    private AppController appsController;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreation() {
        Assert.assertNotNull(appsController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postApp() throws Exception {
        var app = new App();
        app.setId(RandomStringUtils.randomAlphanumeric(10));
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(app);
        mockMvc.perform(post("/create/apps")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getApp() throws Exception{
        mockMvc.perform(get("/apps/0"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }
    
    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllApps() throws Exception{
        mockMvc.perform(get("/admin/apps"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllAppsByOrg() throws Exception {
        mockMvc.perform(get("/organizations/0/apps"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllAppsBySpace() throws Exception {
        mockMvc.perform(get("/spaces/0/apps"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void postAppWithWrongRole() throws Exception {
        var app = new App();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(app);
        mockMvc.perform(post("/create/apps")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAppWithWrongRole() throws Exception{
        mockMvc.perform(get("/apps/0"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllAppsWithWrongRole() throws Exception{
        mockMvc.perform(get("/admin/apps"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllAppsByOrgWithWrongRole() throws Exception {
        mockMvc.perform(get("/organizations/0/apps"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllAppsBySpaceWithWrongRole() throws Exception {
        mockMvc.perform(get("/spaces/0/apps"))
                .andExpect(status().isForbidden());
    }

    @Test
    void postAppWithNoAuth() throws Exception {
        var app = new App();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(app);
        mockMvc.perform(post("/create/apps")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAppWithNoAuth() throws Exception{
        mockMvc.perform(get("/apps/0"))
                .andExpect(status().isUnauthorized());
    }
    
    @Test
    void getAllAppsWithNoAuth() throws Exception{
        mockMvc.perform(get("/admin/apps"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAllAppsByOrgWithNoAuth() throws Exception {
        mockMvc.perform(get("/organizations/0/apps"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAllAppsBySpaceWithNoAuth() throws Exception {
        mockMvc.perform(get("/spaces/0/apps"))
                .andExpect(status().isUnauthorized());
    }

}