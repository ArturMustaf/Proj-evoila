package de.thbingenproj.backend.controller.vdc;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.controller.vapp.vAppController;
import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud.VAppMockCreator;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud.VDCComputeCapacityMockCreator;
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
class VDCComputeCapacityControllerTest {

    @Autowired
    private VDCComputeCapacityController vdcComputeCapacityController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private VDCComputeCapacityMockCreator vdcComputeCapacityMockCreator;

    @Test
    void testCreation() {
        Assert.assertNotNull(vdcComputeCapacityController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postVDCComputeCapacity() throws Exception {
        var vdcComputeCapacity = vdcComputeCapacityMockCreator.createVDCComputeCapacity();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(vdcComputeCapacity);
        mockMvc.perform(post("/create/vdc-compute-capacities")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenPostVDCComputeCapacityURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        var vdcComputeCapacity = vdcComputeCapacityMockCreator.createVDCComputeCapacity();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(vdcComputeCapacity);
        mockMvc.perform(post("/create/vdc-compute-capacities")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getVDCComputeCapacity() throws Exception {
        mockMvc.perform(get("/vdc-compute-capacities/0"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllVDCComputeCapacityByVDCID() throws Exception {
        mockMvc.perform(get("/vdcs/0/vdc-compute-capacities"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}