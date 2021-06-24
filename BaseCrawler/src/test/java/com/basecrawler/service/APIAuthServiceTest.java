package com.basecrawler.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class APIAuthServiceTest {

    static final APIAuthService authService = new APIAuthService();

    @Test
    void getBearerTokenBadRequest() {
        Exception exception = assertThrows(WebClientResponseException.class, () -> {
            authService.getBearerToken("falscherUsername", "falschesPasswort");
        });
        assertTrue(exception.getMessage().contains("400"));
    }

    @Test
    void getBearerTokenUnauthorized() {
        Exception exception = assertThrows(WebClientResponseException.class, () -> {
            authService.getBearerToken("falscherUsername@test", "falschesPasswort");
        });
        assertTrue(exception.getMessage().contains("401"));
    }
}