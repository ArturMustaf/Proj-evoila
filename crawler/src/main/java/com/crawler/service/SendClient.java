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

/**
 * Client to send Data from Crawler to Backend
 * Requests to backend need to be authorized with a bearer token from Keycloak this is handled with
 * @see KeycloakAuthService
 */
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
    @Autowired
    KeycloakAuthService authService;

    /**
     * base URL depends on the env you want to connect to
     * currently its always directed to prod
     * for local deployment it needs to be changed to "http://localhost:8080/create"
     */
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
        var allOrganizations =  organizationRepository.findAll();
        for (var org:allOrganizations) {
                webClient.post()
                        .uri("/organizations")
                        .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                        .body(Mono.just(org), Organization.class)
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
                    .uri("/org-users")
                    .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                    .body(Mono.just(orgUser), OrgUser.class)
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
                    .uri("/org-user-roles")
                    .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                    .body(Mono.just(orgUserRole), OrgUserRole.class)
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
                    .uri("/vdcs")
                    .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                    .body(Mono.just(vdc), VDC.class)
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
                    .uri("/orgNetwork")
                    .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                    .body(Mono.just(orgNetwork), OrgNetwork.class)
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
                    .uri("/computeCapacity")
                    .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                    .body(Mono.just(computeCapacity), VDCComputeCapacity.class)
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
                    .uri("/storageProfile")
                    .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                    .body(Mono.just(storageProfile), VDCStorageProfile.class)
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
                    .uri("/vapps")
                    .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                    .body(Mono.just(vApp), VApp.class)
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
