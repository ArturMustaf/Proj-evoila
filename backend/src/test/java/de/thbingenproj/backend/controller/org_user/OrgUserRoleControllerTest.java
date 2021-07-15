package de.thbingenproj.backend.controller.org_user;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.controller.vcloud.OrgUserRoleController;
import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entity.OrgUserRole;
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
class OrgUserRoleControllerTest {

    @Autowired
    private OrgUserRoleController orgUserRoleController;
    @Autowired
    private MockMvc mockMvc;

    //ToDo set missing property values or delete commented out set()-methods for mock object
    private OrgUserRole createOrgUserRole() {
        OrgUserRole orgUserRole = new OrgUserRole();
        orgUserRole.setId("test-id");
        //orgUserRole.setHref();
        //orgUserRole.setName();
        //orgUserRole.setOrgID();
        return orgUserRole;
    }

    @Test
    void testCreation() {
        Assertions.assertNotNull(orgUserRoleController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postOrgUserRole() throws Exception {
        var orgUserRole = createOrgUserRole();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(orgUserRole);
        mockMvc.perform(post("/create/org-user-roles")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenPostOrgUserRoleURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        var orgUserRole = createOrgUserRole();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(orgUserRole);
        mockMvc.perform(post("/create/org-user-roles")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getOrgUserRole() throws Exception {
        mockMvc.perform(get("/org-user-roles/0"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllOrgUserRoleByOrgID() throws Exception {
        mockMvc.perform(get("/admin/organizations/0/org-user-roles"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenGetAllOrgUserRoleByOrgIDURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        mockMvc.perform(get("/admin/organizations/0/org-user-roles"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllOrgUserRole() throws Exception {
        mockMvc.perform(get("/admin/org-user-roles"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenGetAllOrgUserRoleURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        mockMvc.perform(get("/admin/org-user-roles"))
                .andExpect(status().isForbidden());
    }
}