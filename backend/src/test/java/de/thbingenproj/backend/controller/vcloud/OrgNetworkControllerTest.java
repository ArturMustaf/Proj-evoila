package de.thbingenproj.backend.controller.vcloud;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.entity.OrgNetwork;
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
class OrgNetworkControllerTest {

    @Autowired
    private OrgNetworkController orgNetworkController;
    @Autowired
    private MockMvc mockMvc;

    private OrgNetwork createOrgNetwork() {
        OrgNetwork orgNetwork = new OrgNetwork();
        orgNetwork.setId("urn:vcloud:network:1fcc8a15-340d-43cc-a200-76c2cbbe70ba");
        orgNetwork.setHref("https://vcloud.example.com/api/network/1fcc8a15-340d-43cc-a200-76c2cbbe70ba");
        orgNetwork.setName("VM Network");
        orgNetwork.setOrgID("urn:vcloud:org:7b832bc5-3d65-45a2-8d35-da28388ab80a");
        orgNetwork.setVdcID("urn:vcloud:vdc:afaafb99-228c-4838-ad07-5bf3aa649d42");
        return orgNetwork;
    }

    @Test
    void testCreation() {
        Assertions.assertNotNull(orgNetworkController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postOrgNetwork() throws Exception {
        var orgNetwork = createOrgNetwork();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(orgNetwork);
        mockMvc.perform(post("/create/org-networks")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenPostOrgNetworkURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        var orgNetwork = createOrgNetwork();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(orgNetwork);
        mockMvc.perform(post("/create/org-networks")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getOrgNetworkByID() throws Exception {
        mockMvc.perform(get("/org-networks/0"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllOrgNetworkByVdcID() throws Exception {
        mockMvc.perform(get("/vdcs/0/org-networks"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllOrgNetwork() throws Exception {
        mockMvc.perform(get("/admin/org-networks"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenGetAllOrgNetworkURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        mockMvc.perform(get("/admin/org-networks"))
                .andExpect(status().isForbidden());
    }
}