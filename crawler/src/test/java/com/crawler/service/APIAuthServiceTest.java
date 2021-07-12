package com.crawler.service;

import com.crawler.extensions.PostgresTest;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
class APIAuthServiceTest {

    static final APIAuthService authService = new APIAuthService();

//    @Test
//    Clientside only test
    void getBearerToken() throws InterruptedException, TimeoutException, IOException {
        var token = authService.getBearerToken();
        Assertions.assertNotNull(token);
    }

}