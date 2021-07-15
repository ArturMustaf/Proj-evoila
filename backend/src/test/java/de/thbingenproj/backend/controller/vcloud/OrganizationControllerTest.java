package de.thbingenproj.backend.controller.vcloud;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.entity.Organization;
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

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private Organization createOrganization() {
        Organization organization = new Organization();
        organization.setId("urn:vcloud:org:7b832bc5-3d65-45a2-8d35-da28388ab80a");
        organization.setHref("https://vcloud.example.com/api/org/7b832bc5-3d65-45a2-8d35-da28388ab80a");
        organization.setDescription("Default organization description");
        organization.setName("Default");
        organization.setFullName("Default Organization");
        organization.setIsEnabled(Boolean.TRUE.toString());
        return organization;
    }

    @Test
    void testCreation() {
        Assertions.assertNotNull(organizationController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postOrganization() throws Exception {
        var organization = createOrganization();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(organization);
        mockMvc.perform(post("/create/organizations")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenPostOrganizationURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        var organization = createOrganization();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(organization);
        mockMvc.perform(post("/create/organizations")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getOrganization() throws Exception {
        mockMvc.perform(get("/organizations/0"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllOrganization() throws Exception {
        mockMvc.perform(get("/admin/organizations"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenGetAllOrganizationURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        mockMvc.perform(get("/admin/organizations"))
                .andExpect(status().isForbidden());
    }
}