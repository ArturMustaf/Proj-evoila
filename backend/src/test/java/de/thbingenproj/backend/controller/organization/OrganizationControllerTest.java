package de.thbingenproj.backend.controller.organization;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.controller.org_user.OrgUserRoleController;
import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud.OrgUserRoleMockCreator;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud.OrganizationMockCreator;
import de.thbingenproj.backend.entity.Organization;
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

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
@Transactional
class OrganizationControllerTest {

    @Autowired
    private OrganizationController organizationController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrganizationMockCreator organizationMockCreator;

    @Test
    void testCreation() {
        Assert.assertNotNull(organizationController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postOrganization() throws Exception {
        var organization = organizationMockCreator.createOrganization();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(organization);
        mockMvc.perform(post("/create/organizations")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenPostOrganizationURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        var organization = organizationMockCreator.createOrganization();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(organization);
        mockMvc.perform(post("/create/organizations")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getOrganization() throws Exception {
        mockMvc.perform(get("/organizations/0"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllOrganization() throws Exception {
        mockMvc.perform(get("/admin/organizations"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenGetAllOrganizationURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        mockMvc.perform(get("/admin/organizations"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}