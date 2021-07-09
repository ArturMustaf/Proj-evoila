package de.thbingenproj.backend.controller.vapp;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.controller.organization.OrganizationController;
import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud.OrganizationMockCreator;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud.VAppMockCreator;
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
class VAppControllerTest {

    @Autowired
    private vAppController vAppController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private VAppMockCreator vAppMockCreator;

    @Test
    void testCreation() {
        Assert.assertNotNull(vAppController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postVApp() throws Exception {
        var vApp = vAppMockCreator.createVApp();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(vApp);
        mockMvc.perform(post("/create/vapps")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenPostVAppURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        var vApp = vAppMockCreator.createVApp();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(vApp);
        mockMvc.perform(post("/create/vapps")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getVApp() throws Exception {
        mockMvc.perform(get("/vapps/0"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllVAppByVdcID() throws Exception {
        mockMvc.perform(get("/vdcs/0/vapps"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllVApp() throws Exception {
        mockMvc.perform(get("/admin/vapps"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenGetAllVAppURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        mockMvc.perform(get("/admin/vapps"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}