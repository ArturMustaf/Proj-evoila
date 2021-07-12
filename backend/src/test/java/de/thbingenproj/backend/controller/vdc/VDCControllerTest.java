package de.thbingenproj.backend.controller.vdc;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud.VDCMockCreator;
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
class VDCControllerTest {

    @Autowired
    private VDCController vdcController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private VDCMockCreator vdcMockCreator;

    @Test
    void testCreation() {
        Assert.assertNotNull(vdcController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postVDC() throws Exception {
        var vdc = vdcMockCreator.createVDC();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(vdc);
        mockMvc.perform(post("/create/vdcs")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenPostVDCComputeCapacityURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        var vdc = vdcMockCreator.createVDC();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(vdc);
        mockMvc.perform(post("/create/vdcs")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getVDC() throws Exception {
        mockMvc.perform(get("/vdcs/0"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllVDCByOrganizationID() throws Exception {
        mockMvc.perform(get("/organizations/0/vdcs"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}