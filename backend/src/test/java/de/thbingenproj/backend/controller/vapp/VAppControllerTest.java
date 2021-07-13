package de.thbingenproj.backend.controller.vapp;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entity.VApp;
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

    //ToDo set missing property values or delete commented out set()-methods for mock object
    private VApp createVApp() {
        VApp vApp = new VApp();
        vApp.setId("urn:vcloud:vapp:bba47763-0ce1-45b9-8470-ea29ba58c52f");
        vApp.setHref("https://vcloud.example.com/api/vApp/vapp-bba47763-0ce1-45b9-8470-ea29ba58c52f");
        vApp.setName("importedVapp");
        vApp.setDescription("VApp Descriptio");
        vApp.setCreationDate("1999-05-31T13:20:00.000-05:00");
        vApp.setIsAutoNature(Boolean.TRUE.toString());
        vApp.setIsBusy(Boolean.TRUE.toString());
        vApp.setIsDeployed(Boolean.TRUE.toString());
        vApp.setIsEnabled(Boolean.TRUE.toString());
        vApp.setIsExpired(Boolean.TRUE.toString());
        vApp.setIsInMaintenanceMode(Boolean.TRUE.toString());
        vApp.setIsPublic(Boolean.TRUE.toString());
        //vApp.setMemoryAllocationMB();
        //vApp.setNumberOfCpus();
        //vApp.setOwnerName();
        vApp.setStatus("8");
        //vApp.setTotalStorageAllocatedMb();
        //vApp.setVdcHRef();
        //vApp.setVdcName();
        //vApp.setVdcID();
        //vApp.setCpuAllocationMhz();
        //vApp.setStorageKB();
        //vApp.setNumberOfVMs();
        //vApp.setCpuAllocationInMhz();
        return vApp;
    }

    @Test
    void testCreation() {
        Assertions.assertNotNull(vAppController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postVApp() throws Exception {
        var vApp = createVApp();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(vApp);
        mockMvc.perform(post("/create/vapps")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenPostVAppURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        var vApp = createVApp();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(vApp);
        mockMvc.perform(post("/create/vapps")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getVApp() throws Exception {
        mockMvc.perform(get("/vapps/0"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllVAppByVdcID() throws Exception {
        mockMvc.perform(get("/vdcs/0/vapps"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllVApp() throws Exception {
        mockMvc.perform(get("/admin/vapps"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenGetAllVAppURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        mockMvc.perform(get("/admin/vapps"))
                .andExpect(status().isForbidden());
    }
}