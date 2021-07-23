package de.thbingenproj.backend.controller.cf;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.entity.Space;
import de.thbingenproj.backend.extensions.BeforeEvery;
import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
import org.junit.jupiter.api.Assertions;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
class SpaceControllerTest {
    @Autowired
    private SpaceController spaceController;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreation() {
        Assertions.assertNotNull(spaceController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postSpace() throws Exception {
        var space = new Space();
        space.setId(RandomStringUtils.randomAlphanumeric(10));
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(space);
        mockMvc.perform(post("/create/spaces")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getSpace() throws Exception{
        mockMvc.perform(get("/spaces/0"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllSpaces() throws Exception{
        mockMvc.perform(get("/admin/spaces"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllSpacesByOrg() throws Exception {
        mockMvc.perform(get("/organizations/0/spaces"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void postSpaceWithWrongRole() throws Exception {
        var space = new Space();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(space);
        mockMvc.perform(post("/create/spaces")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getSpaceWithWrongRole() throws Exception{
        mockMvc.perform(get("/spaces/0"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllSpacesWithWrongRole() throws Exception{
        mockMvc.perform(get("/admin/spaces"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllSpacesByOrgWithWrongRole() throws Exception {
        mockMvc.perform(get("/organizations/0/spaces"))
                .andExpect(status().isForbidden());
    }

    @Test
    void postSpaceWithNoAuth() throws Exception {
        var space = new Space();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(space);
        mockMvc.perform(post("/create/spaces")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getSpaceWithNoAuth() throws Exception{
        mockMvc.perform(get("/spaces/0"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAllSpacesWithNoAuth() throws Exception{
        mockMvc.perform(get("/admin/spaces"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAllSpacesByOrgWithNoAuth() throws Exception {
        mockMvc.perform(get("/organizations/0/spaces"))
                .andExpect(status().isUnauthorized());
    }
}