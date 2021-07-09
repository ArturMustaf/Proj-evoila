package de.thbingenproj.backend.controller.org_user;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.controller.org_network.OrgNetworkController;
import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud.OrgNetworkMockCreator;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud.OrgUserMockCreator;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
class OrgUserControllerTest {

    @Autowired
    private OrgUserController orgUserController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrgUserMockCreator orgUserMockCreator;


    @Test
    void testCreation() {
        Assert.assertNotNull(orgUserController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postOrgUser() throws Exception {
        var orgUser = orgUserMockCreator.createOrgUser();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(orgUser);
        mockMvc.perform(post("/create/org-users")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenPostOrgUserURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        var orgUser = orgUserMockCreator.createOrgUser();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(orgUser);
        mockMvc.perform(post("/create/org-users")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getOrgUser() throws Exception {
        mockMvc.perform(get("/org-users/0"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllOrgUserByOrgID() throws Exception {
        mockMvc.perform(get("/admin/organizations/0/org-users"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllOrgUser() throws Exception {
        mockMvc.perform(get("/admin/org-users"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenGetAllOrgUserURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        mockMvc.perform(get("/admin/org-users"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}