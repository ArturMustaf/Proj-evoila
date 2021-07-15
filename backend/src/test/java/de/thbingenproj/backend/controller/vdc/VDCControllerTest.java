package de.thbingenproj.backend.controller.vdc;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.controller.vcloud.VDCController;
import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entity.VDC;
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
class VDCControllerTest {

    @Autowired
    private VDCController vdcController;
    @Autowired
    private MockMvc mockMvc;

    //ToDo set missing property values or delete commented out set()-methods for mock object
    public VDC createVDC() {
        VDC vdc = new VDC();
        vdc.setId("urn:vcloud:vdc:afaafb99-228c-4838-ad07-5bf3aa649d42");
        vdc.setName("Default vDC");
        vdc.setHref("https://vcloud.example.com/api/vdc/afaafb99-228c-4838-ad07-5bf3aa649d42");
        //vdc.setTasks();
        vdc.setAllocationModel("AllocationVApp");
        //vdc.setOrgID();
        return vdc;
    }

    @Test
    void testCreation() {
        Assertions.assertNotNull(vdcController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postVDC() throws Exception {
        var vdc = createVDC();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(vdc);
        mockMvc.perform(post("/create/vdcs")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenPostVDCComputeCapacityURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        var vdc = createVDC();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(vdc);
        mockMvc.perform(post("/create/vdcs")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getVDC() throws Exception {
        mockMvc.perform(get("/vdcs/0"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllVDCByOrganizationID() throws Exception {
        mockMvc.perform(get("/organizations/0/vdcs"))
                .andExpect(status().isOk());
    }
}