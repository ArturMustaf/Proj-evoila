package com.crawler.service;

import com.crawler.repository.*;
import org.junit.jupiter.api.BeforeAll;
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



    @BeforeAll
    public static void init(){
    }

    @Test
    void postAllOrganizationsTest() {
    }

    @Test
    void postAllOrgUsersTest() {
    }

    @Test
    void postAllUserRolesTest() {
    }

    @Test
    void postAllVDCsTest() {
    }

    @Test
    void postAllNetworksTest() {
    }

    @Test
    void postAllComputeCapacitiesTest() {
    }

    @Test
    void postAllStorageProfilesTest() {
    }

    @Test
    void postAllVAppsTest() {
    }
}