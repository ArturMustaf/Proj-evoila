package com.crawler.service;

import com.crawler.entity.Organization;
import com.crawler.repository.*;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;


@SpringBootTest
class SendClientTest {
    SendClient sendClient;
    @Autowired
    KeycloakAuthService authService;
    @Autowired
    OrganizationRepository organizationRepository;


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
        sendClient = new SendClient(baseUrl);
    }

    @Test
    @Transactional
    void postAllOrganizationsTest() {
        var entities = new ArrayList<Organization>();
        for (int i = 0; i < 5; i++) {
            var org = new Organization();
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
            org.setId(generatedString);
            entities.add(org);
        }
        organizationRepository.saveAll(entities);
        ArrayList<Organization> organizations = organizationRepository.findAll();
        for (var org : organizations) {
            mockServer.enqueue(new MockResponse()
                    .setBody(org.toString()));
        }
        System.out.println(sendClient.postAllOrganizations());
    }

    @Test
    void postAllOrgUsers() {
    }

    @Test
    void postAllUserRoles() {
    }

    @Test
    void postAllVDCs() {
    }

    @Test
    void postAllNetworks() {
    }

    @Test
    void postAllComputeCapacities() {
    }

    @Test
    void postAllStorageProfiles() {
    }

    @Test
    void postAllVApps() {
    }
}