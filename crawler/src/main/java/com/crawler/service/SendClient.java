package com.crawler.service;

import com.crawler.entity.*;
import com.crawler.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
@Service
public class SendClient {
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

    final WebClient webClient = WebClient.builder()
            .baseUrl("http://hatkeinehaende.de/create")
            .defaultHeader(MediaType.APPLICATION_JSON_VALUE)
            .build();
    public void sendAll(){
        postAllOrganizations();
        postAllOrgUsers();
        postAllNetworks();
        postAllComputeCapacities();
        postAllVApps();
        postAllStorageProfiles();
        postAllUserRoles();
        postAllVDCs();
    }
    public void postAllOrganizations(){
        ArrayList<Organization> allOrganizations =  organizationRepository.findAll();
        for (var org:allOrganizations) {
            webClient.post()
                    .uri("/org")
                    .body(org, Organization.class)
                    .retrieve()
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .onStatus(HttpStatus::isError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .bodyToMono(String.class)
                    .block();
        }
    }
    public void postAllOrgUsers(){
        ArrayList<OrgUser> allOrgUsers =  orgUserRepository.findAll();
        for (var orgUser: allOrgUsers) {
            webClient.post()
                    .uri("/user")
                    .body(orgUser, OrgUser.class)
                    .retrieve()
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .onStatus(HttpStatus::isError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .bodyToMono(String.class)
                    .block();
        }
    }
    public void postAllUserRoles(){
        ArrayList<OrgUserRole> allOrgUserRoles= orgUserRoleRepository.findAll();
        for (var orgUserRole: allOrgUserRoles) {
            webClient.post()
                    .uri("/role")
                    .body(orgUserRole, OrgUserRole.class)
                    .retrieve()
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .onStatus(HttpStatus::isError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .bodyToMono(String.class)
                    .block();
        }
    }
    public void postAllVDCs(){
        ArrayList<VDC> allVDCs = vdcRepository.findAll();
        for (var vdc: allVDCs) {
            webClient.post()
                    .uri("/vdc")
                    .body(vdc, VDC.class)
                    .retrieve()
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .onStatus(HttpStatus::isError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .bodyToMono(String.class)
                    .block();
        }
    }
    public void postAllNetworks(){
        ArrayList<OrgNetwork> allOrgNetworks= orgNetworkRepository.findAll();
        for (var orgNetwork: allOrgNetworks) {
            webClient.post()
                    .uri("/network")
                    .body(orgNetwork, OrgNetwork.class)
                    .retrieve()
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .onStatus(HttpStatus::isError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .bodyToMono(String.class)
                    .block();
        }
    }
    public void postAllComputeCapacities(){
        ArrayList<VDCComputeCapacity> allOrgComputeCapacities = computeCapacityRepository.findAll();
        for (var computeCapacity: allOrgComputeCapacities) {
            webClient.post()
                    .uri("/create/computeCapacity")
                    .body(computeCapacity, VDCComputeCapacity.class)
                    .retrieve()
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .onStatus(HttpStatus::isError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .bodyToMono(String.class)
                    .block();
        }
    }
    public void postAllStorageProfiles(){
        ArrayList<VDCStorageProfile> allStorageProfiles = storageProfileRepository.findAll();
        for (var storageProfile: allStorageProfiles) {
            webClient.post()
                    .uri("/create/storageProfile")
                    .body(storageProfile, VDCStorageProfile.class)
                    .retrieve()
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .onStatus(HttpStatus::isError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .bodyToMono(String.class)
                    .block();
        }
    }
    public void postAllVApps(){
        ArrayList<VApp> allVApps = vAppRepository.findAll();
        for (var vApp: allVApps) {
            webClient.post()
                    .uri("/create/vApp")
                    .body(vApp, VApp.class)
                    .retrieve()
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .onStatus(HttpStatus::isError,
                            response -> Mono.error(new Exception(response.statusCode().toString())))
                    .bodyToMono(String.class)
                    .block();
        }
    }

}
