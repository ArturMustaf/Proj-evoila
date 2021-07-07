package com.crawler.service;

import com.crawler.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
class SendClientTest {
    @Autowired
    SendClient sendClient;
    @Autowired
    KeycloakAuthService authService;
    @Autowired
    OrganizationRepository organizationRepository;

    @Test
    void sendClientTest() {
        Assertions.assertNotNull(sendClient);
    }

}