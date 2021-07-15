package de.thbingenproj.backend.controller.vcloud;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.controller.vcloud.OrgUserController;
import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entity.OrgUser;
import de.thbingenproj.backend.extensions.BeforeEvery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
@Transactional
class OrgUserControllerTest {

    @Autowired
    private OrgUserController orgUserController;
    @Autowired
    private MockMvc mockMvc;

    //ToDo set missing property values or delete commented out set()-methods for mock object
    private OrgUser createOrgUser() {
        OrgUser orgUser = new OrgUser();
        orgUser.setId("id");
        orgUser.setHref("href");
        orgUser.setFullName("fullName");
        orgUser.setIsEnabled(Boolean.TRUE.toString());
        orgUser.setIsLocked(Boolean.TRUE.toString());
        //orgUser.setName();
        //orgUser.setEmailAddress();
        //orgUser.setTelephone();
        orgUser.setIsExternal(Boolean.TRUE.toString());
        orgUser.setIsGroupRole(Boolean.TRUE.toString());
        //orgUser.setOrgRoleID();
        //orgUser.setOrgID();
        return orgUser;
    }

    @Test
    void testCreation() {
        Assertions.assertNotNull(orgUserController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postOrgUser() throws Exception {
        var orgUser = createOrgUser();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(orgUser);
        mockMvc.perform(post("/create/org-users")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenPostOrgUserURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        var orgUser = createOrgUser();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(orgUser);
        mockMvc.perform(post("/create/org-users")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getOrgUser() throws Exception {
        mockMvc.perform(get("/org-users/0"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllOrgUserByOrgID() throws Exception {
        mockMvc.perform(get("/admin/organizations/0/org-users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllOrgUser() throws Exception {
        mockMvc.perform(get("/admin/org-users"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenGetAllOrgUserURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        mockMvc.perform(get("/admin/org-users"))
                .andExpect(status().isForbidden());
    }
}