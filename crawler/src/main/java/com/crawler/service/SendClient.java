package com.crawler.service;

import com.crawler.entity.*;
import com.crawler.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.ArrayList;

/**
 * Client to send Data from Crawler to Backend
 * Requests to backend need to be authorized with a bearer token from Keycloak this is handled with
 * @see KeycloakAuthService
 */
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

    WebClient webClient;
    /**
     * base URL depends on the env you want to connect to
     * currently its always directed to prod
     * for local deployment it needs to be changed to "http://localhost:8080/create"
     * for prod its "http://hatkeinehaende.de/create"
     */
    SendClient(String url){
        WebClient.builder()
                .baseUrl(url)
                .defaultHeader(MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public ArrayList<Organization> postAllOrganizations(){
            ArrayList<Organization> allOrganizations = organizationRepository.findAll();
            ArrayList<Organization> postedOrganizations = new ArrayList<>();
            for (var org : allOrganizations) {
                postedOrganizations.add(webClient.post()
                        .uri("/organizations")
                        .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                        .body(Mono.just(org), Organization.class)
                        .retrieve()
                        .bodyToMono(Organization.class)
                        .block());
            }
            return postedOrganizations;
    }
    public ArrayList<String> postAllOrgUsers(){
        ArrayList<OrgUser> allOrgUsers =  orgUserRepository.findAll();
        ArrayList<String> postedOrganizationUsers = new ArrayList<>();
        for (var orgUser: allOrgUsers) {
            postedOrganizationUsers.add(webClient.post()
                    .uri("/org-users")
                    .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                    .body(Mono.just(orgUser), OrgUser.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block());
        }
        return postedOrganizationUsers;
    }
    public ArrayList<String> postAllUserRoles(){
        ArrayList<OrgUserRole> allOrgUserRoles= orgUserRoleRepository.findAll();
        ArrayList<String> postedOrganizationUserRoles = new ArrayList<>();
        for (var orgUserRole: allOrgUserRoles) {
            postedOrganizationUserRoles.add(webClient.post()
                    .uri("/org-user-roles")
                    .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                    .body(Mono.just(orgUserRole), OrgUserRole.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block());
        }
        return postedOrganizationUserRoles;
    }
    public ArrayList<String> postAllVDCs(){
        ArrayList<VDC> allVDCs = vdcRepository.findAll();
        ArrayList<String> postedVDCs = new ArrayList<>();
        for (var vdc: allVDCs) {
            postedVDCs.add(webClient.post()
                    .uri("/vdcs")
                    .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                    .body(Mono.just(vdc), VDC.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block());
        }
        return postedVDCs;
    }
    public ArrayList<String> postAllNetworks(){
        ArrayList<OrgNetwork> allOrgNetworks= orgNetworkRepository.findAll();
        ArrayList<String> postedOrganizationNetworks = new ArrayList<>();
        for (var orgNetwork: allOrgNetworks) {
            postedOrganizationNetworks.add(webClient.post()
                    .uri("/orgNetwork")
                    .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                    .body(Mono.just(orgNetwork), OrgNetwork.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block());
        }
        return postedOrganizationNetworks;
    }
    public ArrayList<String> postAllComputeCapacities(){
        ArrayList<VDCComputeCapacity> allOrgComputeCapacities = computeCapacityRepository.findAll();
        ArrayList<String> postedComputeCapacities= new ArrayList<>();
        for (var computeCapacity: allOrgComputeCapacities) {
            postedComputeCapacities.add(webClient.post()
                    .uri("/computeCapacity")
                    .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                    .body(Mono.just(computeCapacity), VDCComputeCapacity.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block());
        }
        return postedComputeCapacities;
    }
    public ArrayList<String> postAllStorageProfiles(){
        ArrayList<VDCStorageProfile> allStorageProfiles = storageProfileRepository.findAll();
        ArrayList<String> postedStorageProfiles= new ArrayList<>();
        for (var storageProfile: allStorageProfiles) {
            postedStorageProfiles.add(webClient.post()
                    .uri("/storageProfile")
                    .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                    .body(Mono.just(storageProfile), VDCStorageProfile.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block());
        }
        return postedStorageProfiles;
    }
    public ArrayList<String> postAllVApps(){
        ArrayList<VApp> allVApps = vAppRepository.findAll();
        ArrayList<String> postedVApps = new ArrayList<>();
        for (var vApp: allVApps) {
            postedVApps.add(webClient.post()
                    .uri("/vapps")
                    .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                    .body(Mono.just(vApp), VApp.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block());
        }
        return postedVApps;
    }

}
