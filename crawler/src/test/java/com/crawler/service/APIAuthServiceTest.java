package com.crawler.service;

import com.crawler.extensions.PostgresTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
class APIAuthServiceTest {

    @Autowired
    APIAuthService authService;

    /**
     * test should only run on dev machines (windows in our case)
     */
    @DisabledOnOs(OS.LINUX)
    @Test
    void getBearerToken() throws InterruptedException, TimeoutException, IOException {
        var token = authService.getBearerToken();
        Assertions.assertNotNull(token);
    }

}