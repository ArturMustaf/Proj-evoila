package de.thbingenproj.backend.controller.resourceview;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import de.thbingenproj.backend.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.extensions.BeforeEvery;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
class ResourceViewControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ResourceViewController resourceViewController;

    @Test
    void testCreation(){
        Assert.assertNotNull(resourceViewController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void resourceViewTimeframe() throws Exception {
        mockMvc.perform(get("/resourceview").contentType(MediaType.APPLICATION_JSON).param("project", "0").param("from", "0").param("to", "1000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }
    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void resourceViewTimeframeWithWrongRole() throws Exception {
        mockMvc.perform(get("/resourceview").contentType(MediaType.APPLICATION_JSON).param("project", "0").param("from", "0").param("to", "1000"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    void resourceViewTimeframeWithNoAuth() throws Exception {
        mockMvc.perform(get("/resourceview").contentType(MediaType.APPLICATION_JSON).param("project", "0").param("from", "0").param("to", "1000"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}