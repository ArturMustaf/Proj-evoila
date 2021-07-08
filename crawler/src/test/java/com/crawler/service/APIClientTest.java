package com.crawler.service;

import com.crawler.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
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

    @Test
//    Clientside only test
    void genericTestClass(){
        Assertions.assertTrue(apiClient.getAllOrgs());
        System.out.println("Orgs: "+organizationRepository.findAll());
        System.out.println("Networks: "+orgNetworkRepository.findAll());
        System.out.println("OrgUser: "+orgUserRepository.findAll());
        System.out.println("OrgUserRole: "+orgUserRoleRepository.findAll());
        System.out.println("OrgVDCs: "+vdcRepository.findAll());
        System.out.println("ComputeCapacities: " + computeCapacityRepository.findAll());
        System.out.println("StorageProfiles: " + storageProfileRepository.findAll());
        System.out.println("VApps: "+vAppRepository.findAll());
    }

}