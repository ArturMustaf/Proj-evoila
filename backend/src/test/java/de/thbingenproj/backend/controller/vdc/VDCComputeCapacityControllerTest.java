package de.thbingenproj.backend.controller.vdc;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.controller.vcloud.VDCComputeCapacityController;
import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entity.VDCComputeCapacity;
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
class VDCComputeCapacityControllerTest {

    @Autowired
    private VDCComputeCapacityController vdcComputeCapacityController;
    @Autowired
    private MockMvc mockMvc;

    //ToDo set missing property values or delete commented out set()-methods for mock object
    private VDCComputeCapacity createVDCComputeCapacity() {
        VDCComputeCapacity vdcComputeCapacity = new VDCComputeCapacity();
        vdcComputeCapacity.setId(0L);
        vdcComputeCapacity.setCpuUnits("MHz");
        //vdcComputeCapacity.setCpuLimit();
        //vdcComputeCapacity.setCpuAllocated();
        //vdcComputeCapacity.setCpuReserved();
        //vdcComputeCapacity.setCpuUsed();
        //vdcComputeCapacity.setMemoryUsed();
        //vdcComputeCapacity.setMemoryLimit();
        //vdcComputeCapacity.setMemoryAllocated();
        //vdcComputeCapacity.setMemoryReserved();
        //vdcComputeCapacity.setMemoryUsed();
        //vdcComputeCapacity.setDate();
        //vdcComputeCapacity.setVdcID();
        return vdcComputeCapacity;
    }

    @Test
    void testCreation() {
        Assertions.assertNotNull(vdcComputeCapacityController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postVDCComputeCapacity() throws Exception {
        var vdcComputeCapacity = createVDCComputeCapacity();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(vdcComputeCapacity);
        mockMvc.perform(post("/create/vdc-compute-capacities")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenPostVDCComputeCapacityURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        var vdcComputeCapacity = createVDCComputeCapacity();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(vdcComputeCapacity);
        mockMvc.perform(post("/create/vdc-compute-capacities")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getVDCComputeCapacity() throws Exception {
        mockMvc.perform(get("/vdc-compute-capacities/0"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllVDCComputeCapacityByVDCID() throws Exception {
        mockMvc.perform(get("/vdcs/0/vdc-compute-capacities"))
                .andExpect(status().isOk());
    }
}