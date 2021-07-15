package de.thbingenproj.backend.controller.cf;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import de.thbingenproj.backend.extensions.BeforeEvery;
import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
class ArangoControllerTest {
    @Autowired
    ArangoController arangoController;
    @Autowired
    MockMvc mockMvc;

    @Test
    void testCreation(){
            Assert.assertNotNull(arangoController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getLastUpdate() throws Exception{
        mockMvc.perform(get("/arango/lastUpdate"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("0")));
    }
}