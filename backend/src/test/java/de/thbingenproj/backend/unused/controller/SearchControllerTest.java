package de.thbingenproj.backend.unused.controller;

import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
import de.thbingenproj.backend.controller.SearchController;
import de.thbingenproj.backend.unused.entities.repositories.container.PostgresTest;
import de.thbingenproj.backend.extensions.BeforeEvery;
import org.junit.jupiter.api.Assertions;
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
class SearchControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    SearchController searchController;

    @Test
    void testCreation(){
        Assertions.assertNotNull(searchController);
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getCustomers() throws Exception{
        mockMvc.perform(get("/search/customers/test/0/10"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getContactInformation() throws Exception{
        mockMvc.perform(get("/search/contact_information/test/0/10"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getProjects() throws Exception{
        mockMvc.perform(get("/search/projects/test/0/10"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_admin")
    void getResourceType() throws Exception{
        mockMvc.perform(get("/search/resourcetypes/test/0/10"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getCustomersWithWrongRole() throws Exception{
        mockMvc.perform(get("/search/customers/test/0/10"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getContactInformationWithWrongRole() throws Exception{
        mockMvc.perform(get("/search/contact_information/test/0/10"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getProjectsWithWrongRole() throws Exception{
        mockMvc.perform(get("/search/projects/test/0/10"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakAuth("ROLE_user")
    void getResourceTypeWithWrongRole() throws Exception{
        mockMvc.perform(get("/search/resourcetypes/test/0/10"))
                .andExpect(status().isForbidden());
    }

    @Test
    void getCustomersWithNoAuth() throws Exception{
        mockMvc.perform(get("/search/customers/test/0/10"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getContactInformationWithNoAuth() throws Exception{
        mockMvc.perform(get("/search/contact_information/test/0/10"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getProjectsWithNoAuth() throws Exception{
        mockMvc.perform(get("/search/projects/test/0/10"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getResourceTypeWithNoAuth() throws Exception{
        mockMvc.perform(get("/search/resourcetypes/test/0/10"))
                .andExpect(status().isUnauthorized());
    }
}