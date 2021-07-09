package com.crawler.service;

import com.crawler.extensions.PostgresTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import java.io.IOException;

@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
class KeycloakAuthServiceTest {
    @Autowired
    KeycloakAuthService authService;
    @Autowired
    ObjectMapper objectMapper;
    public static MockWebServer mockServer;
    @BeforeAll
    static void setUp() throws IOException {
        mockServer = new MockWebServer();
        mockServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockServer.shutdown();
    }
    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s",
                mockServer.getPort());
        authService.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Test
    void getKeycloakBearerTokenTest() throws JsonProcessingException {
        String generatedString = RandomStringUtils.randomAlphanumeric(20);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("access_token",generatedString);
        mockServer.enqueue(new MockResponse()
                .setHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED)
                .setBody(objectMapper.writeValueAsString(formData)));
        Assertions.assertTrue(authService.getKeycloakBearerToken().contains(generatedString));
    }

}