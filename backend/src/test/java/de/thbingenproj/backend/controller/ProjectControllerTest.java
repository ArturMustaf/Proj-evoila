package de.thbingenproj.backend.controller;

import com.c4_soft.springaddons.security.oauth2.test.annotations.IdTokenClaims;
import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.controller.keycloak.Authenticator;
import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.user_information.CustomerMockCreator;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.user_information.ProjectMockCreator;
import de.thbingenproj.backend.extensions.BeforeEvery;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Id;
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
class ProjectControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ProjectMockCreator projectMockCreator;
    @Autowired
    ProjectController projectController;
    @Autowired
    CustomerMockCreator customerMockCreator;

    @Test
    void testCreation() {
        Assert.assertNotNull(projectController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getProjects() throws Exception {
        mockMvc.perform(get("/create/projects/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void putProjects() throws Exception{
        var project = projectMockCreator.createProject();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(project);
        mockMvc.perform(put("/create/projects/0")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postProjects() throws Exception {
        var project = projectMockCreator.createProject();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(project);
        mockMvc.perform(post("/create/projects")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllProjects() throws Exception{
        mockMvc.perform(get("/projectInformation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getProjectsWithWrongRole() throws Exception {
        mockMvc.perform(get("/create/projects/0"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void putProjectsWithWrongRole() throws Exception{
        var project = projectMockCreator.createProject();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(project);
        mockMvc.perform(put("/create/projects/0")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void postProjectsWithWrongRole() throws Exception {
        var project = projectMockCreator.createProject();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(project);
        mockMvc.perform(post("/create/projects")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllProjectsWithWrongRole() throws Exception{
        mockMvc.perform(get("/projectInformation"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getProjectsWithNoAuth() throws Exception {
        mockMvc.perform(get("/create/projects/0"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void putProjectsWithNoAuth() throws Exception{
        var project = projectMockCreator.createProject();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(project);
        mockMvc.perform(put("/create/projects/0")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void postProjectsWithNoAuth() throws Exception {
        var project = projectMockCreator.createProject();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(project);
        mockMvc.perform(post("/create/projects")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAllProjectsWithNoAuth() throws Exception{
        mockMvc.perform(get("/projectInformation"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}