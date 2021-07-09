package de.thbingenproj.backend.controller.vdc;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud.VDCMockCreator;
import de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud.VDCStorageProfileMockCreator;
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
class VDCStorageProfileControllerTest {

    @Autowired
    private VDCStorageProfileController vdcStorageProfileController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private VDCStorageProfileMockCreator vdcStorageProfileMockCreator;

    @Test
    void testCreation() {
        Assert.assertNotNull(vdcStorageProfileController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postVDCStorageProfile() throws Exception {
        var vdcStorageProfile = vdcStorageProfileMockCreator.createVDCStorageProfile();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(vdcStorageProfile);
        mockMvc.perform(post("/create/vdc-storage-profiles")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void givenPostVDCStorageProfileURIWithUserRole_whenMockMVC_thenResponseForbidden() throws Exception {
        var vdcStorageProfile = vdcStorageProfileMockCreator.createVDCStorageProfile();
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