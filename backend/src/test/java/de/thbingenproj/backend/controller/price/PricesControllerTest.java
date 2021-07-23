package de.thbingenproj.backend.controller.price;


import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thbingenproj.backend.entity.Prices;
import de.thbingenproj.backend.entity.ResType;
import de.thbingenproj.backend.extensions.BeforeEvery;
import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
class PricesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PricesController pricesController;

    @Transactional
    @Test
    void testCreation() {
        Assertions.assertNotNull(pricesController);
    }

    @Transactional
    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postPricesTest() throws Exception {
        var prices = new Prices();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(prices);
        mockMvc.perform(post("/create/prices")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }
    @Transactional
    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void postPricesWithWrongRoleTest() throws Exception {
        var prices = new Prices();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(prices);
        mockMvc.perform(post("/create/prices")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isForbidden());
    }
    @Transactional
    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postUpdatePricesTest() throws Exception {
        var prices = new Prices();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(prices);
        mockMvc.perform(post("/create/resources/0/prices")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }
    @Transactional
    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void postUpdatePricesWithWrongRoleTest() throws Exception {
        var prices = new Prices();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(prices);
        mockMvc.perform(post("/create/resources/0/prices")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isForbidden());
    }
    @Transactional
    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllPricesAdminTest() throws Exception {
        mockMvc.perform(get("/admin/prices"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }
    @Transactional
    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllPricesWithWrongRoleTest() throws Exception {
        mockMvc.perform(get("/admin/prices"))
                .andExpect(status().isForbidden());
    }

    @Transactional
    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getPriceTest() throws Exception {
        mockMvc.perform(get("/prices/0"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Transactional
    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllPricesByRessourceIdTest() throws Exception {
        mockMvc.perform(get("/resources/5/prices"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }
    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllPricesByOrgIDTest() throws Exception {
        mockMvc.perform(get("/organizations/prices/0"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllPricesByVDCIDTest() throws Exception {
        mockMvc.perform(get("/vdcs/prices/0"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getPricesByVdcIdInGivenTimeTest() throws Exception {
        mockMvc.perform(get("/vdcs/prices/0/100/200"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }
    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getPricesByOrgIdInGivenTimeTest() throws Exception {
        mockMvc.perform(get("/organizations/prices/0/100/200"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    //no auth tests
    @Test
    void postPriceWithNoAuth() throws Exception {
        var resourceType = new ResType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/create/prices")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void updatePriceWithNoAuth() throws Exception {
        var resourceType = new ResType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/create/resources/5/prices")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void getPriceWithNoAuth() throws Exception {
        var resourceType = new ResType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/prices/8")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void getAllPriceWithNoAuth() throws Exception {
        var resourceType = new ResType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/admin/prices")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void getAllPricesByRessourceIdWithNoAuth() throws Exception {
        var resourceType = new ResType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/resources/8/prices")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void getAllPricesByOrgIDWithNoAuth() throws Exception {
        var resourceType = new ResType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/organizations/prices/0")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void getAllPricesByVdcIdWithNoAuth() throws Exception {
        var resourceType = new ResType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/vdcs/prices/0")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void getPricesByOrgIdInGivenTimeWithNoAuth() throws Exception {
        var resourceType = new ResType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/organizations/prices/0/200/5890")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void getPricesByVdcIDInGivenTimeWithNoAuth() throws Exception {
        var resourceType = new ResType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/vdcs/prices/0/200/5890")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }

    @Transactional
    @Test
    @WithMockKeycloakAuth("ROLE_noUser")
    void getPriceTestsWithWrongRoleTest() throws Exception {
        mockMvc.perform(get("/prices/0"))
                .andExpect(status().isForbidden());
    }

    @Transactional
    @Test
    @WithMockKeycloakAuth("ROLE_noUser")
    void getAllPricesByRessourceIdWithWrongRoleTest() throws Exception {
        mockMvc.perform(get("/resources/5/prices"))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockKeycloakAuth("ROLE_noUser")
    void getAllPricesByOrgIDWithWrongRoleTest() throws Exception {
        mockMvc.perform(get("/organizations/prices/0"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_noUser")
    void getAllPricesByVDCIDWithWrongRoleTest() throws Exception {
        mockMvc.perform(get("/vdcs/prices/0"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_noUser")
    void getPricesByVdcIdInGivenTimesWithWrongRoleTest() throws Exception {
        mockMvc.perform(get("/vdcs/prices/0/100/200"))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockKeycloakAuth("ROLE_noUser")
    void getPricesByOrgIdInGivenTimesWithWrongRoleTest() throws Exception {
        mockMvc.perform(get("/organizations/prices/0/100/200"))
                .andExpect(status().isForbidden());
    }
}


