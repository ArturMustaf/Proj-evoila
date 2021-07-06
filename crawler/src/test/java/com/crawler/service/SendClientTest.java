package com.crawler.service;

import com.crawler.entity.Organization;
import com.crawler.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@SpringBootTest
class SendClientTest {
    @Autowired
    SendClient sendClient;
    @Autowired
    OrganizationRepository organizationRepository;


    @Test
    void postAllOrganizationsTest() {
        List<Organization> entities = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            var org = new Organization();
            byte[] array = new byte[7];
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
            org.setId(generatedString);
            entities.add(org);
        }
        organizationRepository.saveAll(entities);
        sendClient.postAllOrganizations();
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