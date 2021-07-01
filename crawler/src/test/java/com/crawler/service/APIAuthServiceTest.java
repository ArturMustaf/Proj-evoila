package com.crawler.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class APIAuthServiceTest {

    static final APIAuthService authService = new APIAuthService();
    String username;
    String password;

    public void getProps() throws IOException {
        String propFileName = "logindata.properties";
        Properties prop = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath / resources. Please create the file and set user and password.");
        }

        // get the property value and print it out
        username = prop.getProperty("user");
        password = prop.getProperty("password");

    }

//    @Test
//    Clientside only test
    void getBearerToken() throws InterruptedException, TimeoutException, IOException {
        getProps();
        var token = authService.getBearerToken(username, password);
        Assertions.assertNotNull(token);
    }

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