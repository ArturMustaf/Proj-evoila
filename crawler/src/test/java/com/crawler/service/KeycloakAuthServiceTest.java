package com.crawler.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import java.io.IOException;

@SpringBootTest
class KeycloakAuthServiceTest {
    @Autowired
    KeycloakAuthService authClient;
    @Autowired
    ObjectMapper objectMapper;
    public static MockWebServer mockServer;
    @BeforeEach
    void setUp() throws IOException {
        mockServer = new MockWebServer();
        mockServer.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockServer.shutdown();
    }
    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s",
                mockServer.getPort());
        authClient.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Test
    void getKeycloakBearerTokenTest() throws JsonProcessingException {
        authClient.clientID = "0";
        authClient.clientSecret = "0";
        authClient.grantType = "0";
        String generatedString = RandomStringUtils.randomAlphanumeric(20);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("access_token",generatedString);
        mockServer.enqueue(new MockResponse()
                .setHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED)
                .setBody(objectMapper.writeValueAsString(formData)));
        Assertions.assertTrue(authClient.getKeycloakBearerToken().contains(generatedString));
    }
}