package com.crawler.service;

import com.crawler.entity.*;
import com.crawler.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

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
     * for prod its "http://hatkeinehaende.de/create"
     */
    WebClient webClient = WebClient.builder()
            .baseUrl("http://hatkeinehaende.de/create")
                .defaultHeader(MediaType.APPLICATION_JSON_VALUE)
                .build();
    Logger logger = LoggerFactory.getLogger(SendClient.class);

    public List<Organization> postAllOrganizations() {
        var allOrganizations = organizationRepository.findAll();
        var postedOrganizations = new ArrayList<Organization>();
        for (var org : allOrganizations) {
            try {
                postedOrganizations.add(webClient.post()
                        .uri("/organizations")
                        .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                        .body(Mono.just(org), Organization.class)
                        .retrieve()
                        .bodyToMono(Organization.class)
                        .block());
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        organizationRepository.deleteAll(postedOrganizations);
        return postedOrganizations;
    }
    public List<OrgUser> postAllOrgUsers() {
        ArrayList<OrgUser> allOrgUsers =  orgUserRepository.findAll();
        var postedOrganizationUsers = new ArrayList<OrgUser>();
        for (var orgUser: allOrgUsers) {
            try {
            postedOrganizationUsers.add(webClient.post()
                    .uri("/org-users")
                    .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                    .body(Mono.just(orgUser), OrgUser.class)
                    .retrieve()
                    .bodyToMono(OrgUser.class)
                    .block());
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        orgUserRepository.deleteAll(postedOrganizationUsers);
        return postedOrganizationUsers;
    }
    public List<OrgUserRole> postAllUserRoles(){
        ArrayList<OrgUserRole> allOrgUserRoles= orgUserRoleRepository.findAll();
        var postedOrganizationUserRoles = new ArrayList<OrgUserRole>();
        for (var orgUserRole: allOrgUserRoles) {
            try {
                postedOrganizationUserRoles.add(webClient.post()
                        .uri("/org-user-roles")
                        .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                        .body(Mono.just(orgUserRole), OrgUserRole.class)
                        .retrieve()
                        .bodyToMono(OrgUserRole.class)
                        .block());
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        orgUserRoleRepository.deleteAll(postedOrganizationUserRoles);
        return postedOrganizationUserRoles;
    }
    public List<VDC> postAllVDCs(){
        ArrayList<VDC> allVDCs = vdcRepository.findAll();
        var postedVDCs = new ArrayList<VDC>();
        for (var vdc: allVDCs) {
            try {
            postedVDCs.add(webClient.post()
                    .uri("/vdcs")
                    .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                    .body(Mono.just(vdc), VDC.class)
                    .retrieve()
                    .bodyToMono(VDC.class)
                    .block());
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        vdcRepository.deleteAll(postedVDCs);
        return postedVDCs;
    }
    public List<OrgNetwork> postAllNetworks(){
        ArrayList<OrgNetwork> allOrgNetworks= orgNetworkRepository.findAll();
        var postedOrganizationNetworks = new ArrayList<OrgNetwork>();
        for (var orgNetwork: allOrgNetworks) {
            try {
                postedOrganizationNetworks.add(webClient.post()
                        .uri("/orgNetwork")
                        .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                        .body(Mono.just(orgNetwork), OrgNetwork.class)
                        .retrieve()
                        .bodyToMono(OrgNetwork.class)
                        .block());
                }
                catch (Exception e) {
                logger.error(e.getMessage());
                }
        }
        orgNetworkRepository.deleteAll(postedOrganizationNetworks);
        return postedOrganizationNetworks;
    }
    public List<VDCComputeCapacity> postAllComputeCapacities(){
        ArrayList<VDCComputeCapacity> allOrgComputeCapacities = computeCapacityRepository.findAll();
        var postedComputeCapacities= new ArrayList<VDCComputeCapacity>();
        for (var computeCapacity: allOrgComputeCapacities) {
            try {
                postedComputeCapacities.add(webClient.post()
                        .uri("/computeCapacity")
                        .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                        .body(Mono.just(computeCapacity), VDCComputeCapacity.class)
                        .retrieve()
                        .bodyToMono(VDCComputeCapacity.class)
                        .block());
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        computeCapacityRepository.deleteAll(postedComputeCapacities);
        return postedComputeCapacities;
    }
    public List<VDCStorageProfile> postAllStorageProfiles(){
        ArrayList<VDCStorageProfile> allStorageProfiles = storageProfileRepository.findAll();
        var postedStorageProfiles= new ArrayList<VDCStorageProfile>();
        for (var storageProfile: allStorageProfiles) {
            try {
                postedStorageProfiles.add(webClient.post()
                        .uri("/storageProfile")
                        .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                        .body(Mono.just(storageProfile), VDCStorageProfile.class)
                        .retrieve()
                        .bodyToMono(VDCStorageProfile.class)
                        .block());
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        storageProfileRepository.deleteAll(postedStorageProfiles);
        return postedStorageProfiles;
    }
    public List<VApp> postAllVApps(){
        ArrayList<VApp> allVApps = vAppRepository.findAll();
        var postedVApps = new ArrayList<VApp>();
        for (var vApp: allVApps) {
            try {
                postedVApps.add(webClient.post()
                        .uri("/vapps")
                        .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                        .body(Mono.just(vApp), VApp.class)
                        .retrieve()
                        .bodyToMono(VApp.class)
                        .block());
            }
            catch (Exception e){
                logger.error(e.getMessage());
            }
        }
        vAppRepository.deleteAll(postedVApps);
        return postedVApps;
    }
    public void sendAll(){
        postAllStorageProfiles();
        postAllComputeCapacities();
        postAllUserRoles();
        postAllVDCs();
        postAllVApps();
        postAllOrganizations();
        postAllNetworks();
        postAllOrgUsers();
    }
}
