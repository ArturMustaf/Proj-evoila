package de.thbingenproj.backend.controller.vdc;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entity.VDCStorageProfile;
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
class VDCStorageProfileControllerTest {

    @Autowired
    private VDCStorageProfileController vdcStorageProfileController;
    @Autowired
    private MockMvc mockMvc;

    //ToDo set missing property values or delete commented out set()-methods for mock object
    public VDCStorageProfile createVDCStorageProfile() {
        VDCStorageProfile vdcStorageProfile = new VDCStorageProfile();
        vdcStorageProfile.setId("urn:vcloud:vdcstorageProfile:eb6aacbd-f0d8-4a07-8248-1de28151e98f");
        vdcStorageProfile.setHref("https://vcloud.example.com/api/vdcStorageProfile/eb6aacbd-f0d8-4a07-8248-1de28151e98f");
        vdcStorageProfile.setName("profile");
        //vdcStorageProfile.setVdcID();
        //vdcStorageProfile.setDate();
        vdcStorageProfile.setEnabled(Boolean.FALSE.toString());
        vdcStorageProfile.setUnits("MB");
        vdcStorageProfile.setMaxLimit(Long.valueOf(301).toString());
        //vdcStorageProfile.setStorageUsedMB();
        //vdcStorageProfile.setIopsAllocated();
        return vdcStorageProfile;
    }

    @Test
    void testCreation() {
        Assertions.assertNotNull(vdcStorageProfileController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postVDCStorageProfile() throws Exception {
        var vdcStorageProfile = createVDCStorageProfile();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(vdcStorageProfile);
        mockMvc.perform(post("/create/vdc-storage-profiles")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenPostVDCStorageProfileURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        var vdcStorageProfile = createVDCStorageProfile();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(vdcStorageProfile);
        mockMvc.perform(post("/create/vdc-storage-profiles")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getVDCStorageProfile() throws Exception {
        mockMvc.perform(get("/vdc-storage-profiles/0"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllVDCStorageProfileByVdcID() throws Exception {
        mockMvc.perform(get("/vdcs/0/vdc-storage-profiles"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}