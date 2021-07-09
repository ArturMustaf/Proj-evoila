package de.thbingenproj.backend.controller.org_network;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud.OrgNetworkMockCreator;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    @Autowired
    private OrgNetworkMockCreator orgNetworkMockCreator;


    @Test
    void testCreation() {
        Assert.assertNotNull(orgNetworkController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postOrgNetwork() throws Exception {
        var orgNetwork = orgNetworkMockCreator.createOrgNetwork();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(orgNetwork);
        mockMvc.perform(post("/create/orgNetwork")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenPostOrgNetworkURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        var orgNetwork = orgNetworkMockCreator.createOrgNetwork();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(orgNetwork);
        mockMvc.perform(post("/create/orgNetwork")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getOrgNetworkByID() throws Exception {
        mockMvc.perform(get("/org-networks/0"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllOrgNetworkByVdcID() throws Exception {
        mockMvc.perform(get("/vdcs/0/org-networks"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllOrgNetwork() throws Exception {
        mockMvc.perform(get("/admin/org-networks"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenGetAllOrgNetworkURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        mockMvc.perform(get("/admin/org-networks"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}