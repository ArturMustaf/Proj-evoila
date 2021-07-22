package de.thbingenproj.backend.controller.resources;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import com.fasterxml.jackson.databind.ObjectMapper;


import de.thbingenproj.backend.entity.ResType;
import de.thbingenproj.backend.entity.Resource;
import de.thbingenproj.backend.extensions.BeforeEvery;
import de.thbingenproj.backend.repository.ResourceRepository;
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


import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ExtendWith(BeforeEvery.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@AutoConfigureMockMvc
public class ResourceControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    ResourceController resourceController;

    @Test
    void testCreation() {
        Assert.assertNotNull(resourceController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void postResourceTest() throws Exception {
        var resource = new Resource();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resource);
        mockMvc.perform(post("/create/resources")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getAllResourcesTest()throws Exception{
        mockMvc.perform(get("/admin/resources"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }


    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getResourceTest() throws Exception {
        mockMvc.perform(get("/resources/0"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }



    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllResourcesByOrgIDTest() throws Exception {
        mockMvc.perform(get("/organizations/resources/0"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllResourcesByVDCIDTest() throws Exception {
        mockMvc.perform(get("/vdcs/resources/0"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getResourcesByVdcIdInGivenTimeTest() throws Exception {
        mockMvc.perform(get("/vdcs/resources/0/100/200"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }
    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getResourcesByOrgIdInGivenTimeTest() throws Exception {
        mockMvc.perform(get("/organizations/0/resources/100/200"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }
    //no auth
    @Test
    void postResourceWithNoAuth() throws Exception {
        var resourceType = new ResType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/create/resources")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void getResourceWithNoAuth() throws Exception {
        var resourceType = new ResType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/resources/0")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void  getAllResourcesWithNoAuth() throws Exception {
        var resourceType = new ResType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/admin/resources")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void getALlResourcesByOrgIDWithNoAuth() throws Exception {
        var resourceType = new ResType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/organizations/resources/0")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void getALlResourcesByVdcIDWithNoAuth() throws Exception {
        var resourceType = new ResType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/vdcs/resources/0")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void getResourcesByOrgIdInGivenTimeWithNoAuth() throws Exception {
        var resourceType = new ResType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/organizations/resources/0/200025/534631")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void getResourcesByVdcIdInGivenTimeWithNoAuth() throws Exception {
        var resourceType = new ResType();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resourceType);
        mockMvc.perform(post("/vdcs/resources//0/200025/534631")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }
    //wrong role
    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void postResourceWithWrongRoleTest() throws Exception {
        var resource = new Resource();
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resource);
        mockMvc.perform(post("/create/resources")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isForbidden());
    }    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getAllResourcesWithWrongRoleTest() throws Exception {
        mockMvc.perform(get("/admin/resources"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_noUser")
    void getResourceWithWrongRoleTest() throws Exception {
        mockMvc.perform(get("/resources/0"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockKeycloakAuth("ROLE_noUser")
    void getAllResourcesByOrgIDWithWrongRoleTest() throws Exception {
        mockMvc.perform(get("/organizations/resources/0"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_noUser")
    void getAllResourcesByVDCIDWithWrongRoleTest() throws Exception {
        mockMvc.perform(get("/vdcs/resources/0"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_noUser")
    void getResourcesByVdcIdInGivenTimeWithWrongRoleTest() throws Exception {
        mockMvc.perform(get("/vdcs/resources/0/100/200"))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockKeycloakAuth("ROLE_noUser")
    void getResourcesByOrgIdInGivenTimeWithWrongRoleTest() throws Exception {
        mockMvc.perform(get("/organizations/resources/0/100/200"))
                .andExpect(status().isForbidden());
    }

}

