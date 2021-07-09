package com.crawler.service;

import com.crawler.entity.*;
import com.crawler.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;


@SpringBootTest
class SendClientTest {
    @Autowired
    SendClient sendClient;
    @Autowired
    KeycloakAuthService authService;
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    OrgUserRepository orgUserRepository;
    @Autowired
    OrgUserRoleRepository orgUserRoleRepository;
    @Autowired
    OrgNetworkRepository orgNetworkRepository;
    @Autowired
    VDCRepository vdcRepository;
    @Autowired
    VDCComputeCapacityRepository computeCapacityRepository;
    @Autowired
    VDCStorageProfileRepository storageProfileRepository;
    @Autowired
    VAppRepository vAppRepository;
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
        sendClient.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(MediaType.APPLICATION_JSON_VALUE)
                .build();
        authService.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
    @Test
    @Transactional
    void postAllOrganizationsTest() throws JsonProcessingException {
        var entities = new ArrayList<Organization>();
        for (int i = 0; i < 50; i++) {
            var org = new Organization();
            String generatedString = RandomStringUtils.randomAlphanumeric(7);
            org.setId(generatedString);
            entities.add(org);
        }
        organizationRepository.saveAll(entities);
        ArrayList<Organization> organizations = organizationRepository.findAll();
        for (var org : organizations) {
            String generatedString = RandomStringUtils.randomAlphanumeric(20);
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("access_token",generatedString);
            mockServer.enqueue(new MockResponse()
                    .setHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED)
                    .setBody(objectMapper.writeValueAsString(formData)));

            mockServer.enqueue(new MockResponse()
                    .setHeader("Content-Type", "application/json")
                    .setBody(objectMapper.writeValueAsString(org)));
        }
        Assertions.assertIterableEquals(sendClient.postAllOrganizations(),organizations);
    }

    @Test
    @Transactional
    void postAllOrgUsersTest() throws JsonProcessingException {
        var entities = new ArrayList<OrgUser>();
        for (int i = 0; i < 50; i++) {
            String generatedString = RandomStringUtils.randomAlphanumeric(7);
            var orgUser = new OrgUser();
            orgUser.setId(generatedString);
            entities.add(orgUser);
        }
        orgUserRepository.saveAll(entities);
        var orgUsers = orgUserRepository.findAll();
        for (var orgUser : orgUsers) {
            String generatedString = RandomStringUtils.randomAlphanumeric(20);
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("access_token",generatedString);
            mockServer.enqueue(new MockResponse()
                    .setHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED)
                    .setBody(objectMapper.writeValueAsString(formData)));

            mockServer.enqueue(new MockResponse()
                    .setHeader("Content-Type", "application/json")
                    .setBody(objectMapper.writeValueAsString(orgUser)));
        }
        Assertions.assertIterableEquals(sendClient.postAllOrgUsers(),orgUsers);
    }

    @Test
    @Transactional
    void postAllUserRolesTest() throws JsonProcessingException {
        var entities = new ArrayList<OrgUserRole>();
        for (int i = 0; i < 50; i++) {
            String generatedString = RandomStringUtils.randomAlphanumeric(7);
            var orgUserRole = new OrgUserRole();
            orgUserRole.setId(generatedString);
            entities.add(orgUserRole);
        }
        orgUserRoleRepository.saveAll(entities);
        var orgUserRoles = orgUserRoleRepository.findAll();
        for (var orgUserRole : orgUserRoles) {
            String generatedString = RandomStringUtils.randomAlphanumeric(20);
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("access_token",generatedString);
            mockServer.enqueue(new MockResponse()
                    .setHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED)
                    .setBody(objectMapper.writeValueAsString(formData)));

            mockServer.enqueue(new MockResponse()
                    .setHeader("Content-Type", "application/json")
                    .setBody(objectMapper.writeValueAsString(orgUserRole)));
        }
        Assertions.assertIterableEquals(sendClient.postAllUserRoles(),orgUserRoles);
    }

    @Test
    void postAllVDCs() throws JsonProcessingException {
        var entities = new ArrayList<VDC>();
        for (int i = 0; i < 50; i++) {
            String generatedString = RandomStringUtils.randomAlphanumeric(7);
            var vdc = new VDC();
            vdc.setId(generatedString);
            entities.add(vdc);
        }
        vdcRepository.saveAll(entities);
        var vdcs = vdcRepository.findAll();
        for (var vdc : vdcs) {
            String generatedString = RandomStringUtils.randomAlphanumeric(20);
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("access_token",generatedString);
            mockServer.enqueue(new MockResponse()
                    .setHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED)
                    .setBody(objectMapper.writeValueAsString(formData)));

            mockServer.enqueue(new MockResponse()
                    .setHeader("Content-Type", "application/json")
                    .setBody(objectMapper.writeValueAsString(vdc)));
        }
        Assertions.assertIterableEquals(sendClient.postAllVDCs(),vdcs);
    }

    @Test
    @Transactional
    void postAllNetworks() throws JsonProcessingException {
        var entities = new ArrayList<OrgNetwork>();
        for (int i = 0; i < 50; i++) {
            String generatedString = RandomStringUtils.randomAlphanumeric(7);
            var orgNetwork = new OrgNetwork();
            orgNetwork.setId(generatedString);
            entities.add(orgNetwork);
        }
        orgNetworkRepository.saveAll(entities);
        var orgNetworks = orgNetworkRepository.findAll();
        for (var orgNetwork : orgNetworks) {
            String generatedString = RandomStringUtils.randomAlphanumeric(20);
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("access_token",generatedString);
            mockServer.enqueue(new MockResponse()
                    .setHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED)
                    .setBody(objectMapper.writeValueAsString(formData)));

            mockServer.enqueue(new MockResponse()
                    .setHeader("Content-Type", "application/json")
                    .setBody(objectMapper.writeValueAsString(orgNetwork)));
        }
        Assertions.assertIterableEquals(sendClient.postAllNetworks(),orgNetworks);
    }

    @Test
    @Transactional
    void postAllComputeCapacities() throws JsonProcessingException {
        var entities = new ArrayList<VDCComputeCapacity>();
        for (int i = 0; i < 50; i++) {
            var vdcComputeCapacity = new VDCComputeCapacity();
            entities.add(vdcComputeCapacity);
        }
        computeCapacityRepository.saveAll(entities);
        var computeCapacities = computeCapacityRepository.findAll();
        for (var computeCapacity : computeCapacities) {
            String generatedString = RandomStringUtils.randomAlphanumeric(20);
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("access_token",generatedString);
            mockServer.enqueue(new MockResponse()
                    .setHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED)
                    .setBody(objectMapper.writeValueAsString(formData)));

            mockServer.enqueue(new MockResponse()
                    .setHeader("Content-Type", "application/json")
                    .setBody(objectMapper.writeValueAsString(computeCapacity)));
        }
        Assertions.assertIterableEquals(sendClient.postAllComputeCapacities(),computeCapacities);
    }

    @Test
    @Transactional
    void postAllStorageProfiles() throws JsonProcessingException {
        var entities = new ArrayList<VDCStorageProfile>();
        for (int i = 0; i < 50; i++) {
            var vdcStrorageProfile = new VDCStorageProfile();
            String generatedString = RandomStringUtils.randomAlphanumeric(7);
            vdcStrorageProfile.setId(generatedString);
            entities.add(vdcStrorageProfile);
        }
        storageProfileRepository.saveAll(entities);
        var storageProfiles = storageProfileRepository.findAll();
        for (var storageProfile : storageProfiles) {
            String generatedString = RandomStringUtils.randomAlphanumeric(20);
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("access_token",generatedString);
            mockServer.enqueue(new MockResponse()
                    .setHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED)
                    .setBody(objectMapper.writeValueAsString(formData)));

            mockServer.enqueue(new MockResponse()
                    .setHeader("Content-Type", "application/json")
                    .setBody(objectMapper.writeValueAsString(storageProfile)));
        }
        Assertions.assertIterableEquals(sendClient.postAllStorageProfiles(),storageProfiles);
    }

    @Test
    @Transactional
    void postAllVApps() throws JsonProcessingException {
        var entities = new ArrayList<VApp>();
        for (int i = 0; i < 50; i++) {
            String generatedString = RandomStringUtils.randomAlphanumeric(7);
            var vapp = new VApp();
            vapp.setId(generatedString);
            entities.add(vapp);
        }
        vAppRepository.saveAll(entities);
        var vApps = vAppRepository.findAll();
        for (var vapp : vApps) {
            String generatedString = RandomStringUtils.randomAlphanumeric(20);
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("access_token",generatedString);
            mockServer.enqueue(new MockResponse()
                    .setHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED)
                    .setBody(objectMapper.writeValueAsString(formData)));

            mockServer.enqueue(new MockResponse()
                    .setHeader("Content-Type", "application/json")
                    .setBody(objectMapper.writeValueAsString(vapp)));
        }
        Assertions.assertIterableEquals(sendClient.postAllVApps(),vApps);
    }
}