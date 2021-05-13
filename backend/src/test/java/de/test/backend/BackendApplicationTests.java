package de.test.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.test.backend.entities.repositories.container.PostgresTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
//@ContextConfiguration(initializers = {LocalInitalizer.PropertiesInitializer.class})
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
//@ContextConfiguration(initializers = {LocalInitalizer.PropertiesInitializer.class})

        // TODO remove not in use
class BackendApplicationTests {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void before() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


}
