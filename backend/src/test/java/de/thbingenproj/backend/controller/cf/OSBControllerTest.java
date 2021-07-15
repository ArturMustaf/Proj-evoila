package de.thbingenproj.backend.controller.cf;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.entity.OSB;
import de.thbingenproj.backend.extensions.BeforeEvery;
import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
class OSBControllerTest {
    @Autowired
    private OSBController osbController;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreation() {
        Assert.assertNotNull(osbController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postOSB() throws Exception {
        var osb = new OSB();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(osb);
        mockMvc.perform(post("/create/osbs")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getOSB() throws Exception{
        mockMvc.perform(get("/osbs/0"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllOSBs() throws Exception{
        mockMvc.perform(get("/admin/osbs"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void postOSBWithWrongRole() throws Exception {
        var osb = new OSB();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(osb);
        mockMvc.perform(post("/create/osbs")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getOSBWithWrongRole() throws Exception{
        mockMvc.perform(get("/osbs/0"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllOSBsWithWrongRole() throws Exception{
        mockMvc.perform(get("/admin/osbs"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    void postOSBWithNoAuth() throws Exception {
        var osb = new OSB();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(osb);
        mockMvc.perform(post("/create/osbs")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getOSBWithNoAuth() throws Exception{
        mockMvc.perform(get("/osbs/0"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAllOSBsWithNoAuth() throws Exception{
        mockMvc.perform(get("/admin/osbs"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }
}