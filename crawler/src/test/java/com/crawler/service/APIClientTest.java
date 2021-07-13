package com.crawler.service;

import com.crawler.extensions.PostgresTest;
import com.crawler.postgres.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
class APIClientTest {
    @Autowired
    APIClient apiClient;
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    OrgUserRepository orgUserRepository;
    @Autowired
    VDCRepository vdcRepository;
    @Autowired
    OrgUserRoleRepository orgUserRoleRepository;
    @Autowired
    OrgNetworkRepository orgNetworkRepository;
    @Autowired
    VDCComputeCapacityRepository computeCapacityRepository;
    @Autowired
    VDCStorageProfileRepository storageProfileRepository;
    @Autowired
    VAppRepository vAppRepository;

    @Test
    void testGeneration(){
        Assertions.assertNotNull(apiClient);
        Assertions.assertNotNull(organizationRepository);
        Assertions.assertNotNull(orgNetworkRepository);
        Assertions.assertNotNull(orgUserRepository);
        Assertions.assertNotNull(orgUserRoleRepository);
        Assertions.assertNotNull(computeCapacityRepository);
        Assertions.assertNotNull(storageProfileRepository);
        Assertions.assertNotNull(vAppRepository);
        Assertions.assertNotNull(vdcRepository);
    }

}