package com.crawler.service;

import com.crawler.postgres.entity.*;
import com.crawler.postgres.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Client to send Data from Crawler to Backend
 * Requests to backend need to be authorized with a bearer token from Keycloak this is handled with
 * @see KeycloakAuthService
 */

@PropertySource("classpath:logindata.properties")
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
    @Autowired
    AppRepository appRepository;
    @Autowired
    SpaceRepository spaceRepository;
    @Autowired
    OSBRepository osbRepository;
    @Value("${backend.baseURL}")
    String baseURL;

    /**
     * base URL depends on the env you want to connect to
     * currently its always directed to prod
     * for local deployment it needs to be changed to "http://localhost:8080/create"
     * for prod its "http://hatkeinehaende.de/create"
     */
    WebClient webClient;
    final Logger logger = LoggerFactory.getLogger(SendClient.class);

    @PostConstruct
    void initWebclient(){
        webClient = WebClient.builder()
                .baseUrl(baseURL+"create")
                .defaultHeader(MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    /**
     * Posts all entities of organizations that are found and deletes the actually sent entities.
     * @return a list of sent organizations
     */
    public List<Organization> postAllOrganizations() {
        var entities = organizationRepository.findAll();
        var postedEntities = new ArrayList<Organization>();
        for (var entity : entities) {
            try {
                postedEntities.add(webClient.post()
                        .uri("/organizations")
                        .headers(h -> h.setBearerAuth(""+authService.getKeycloakBearerToken()))
                        .body(Mono.just(entity), Organization.class)
                        .retrieve()
                        .bodyToMono(Organization.class)
                        .block());
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        organizationRepository.deleteAll(postedEntities);
        return postedEntities;
    }
    /**
     * Posts all entities of organization users that are found and deletes the actually sent entities.
     * @return a list of sent organization users
     */
    public List<OrgUser> postAllOrgUsers() {
        var entities =  orgUserRepository.findAll();
        var postedEntities = new ArrayList<OrgUser>();
        for (var entity: entities) {
            try {
            postedEntities.add(webClient.post()
                    .uri("/org-users")
                    .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                    .body(Mono.just(entity), OrgUser.class)
                    .retrieve()
                    .bodyToMono(OrgUser.class)
                    .block());
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        orgUserRepository.deleteAll(postedEntities);
        return postedEntities;
    }
    /**
     * Posts all entities of user roles that are found and deletes the actually sent entities.
     * @return a list of sent user roles
     */
    public List<OrgUserRole> postAllUserRoles(){
        var entities= orgUserRoleRepository.findAll();
        var postedEntities = new ArrayList<OrgUserRole>();
        for (var entity: entities) {
            try {
                postedEntities.add(webClient.post()
                        .uri("/org-user-roles")
                        .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                        .body(Mono.just(entity), OrgUserRole.class)
                        .retrieve()
                        .bodyToMono(OrgUserRole.class)
                        .block());
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        orgUserRoleRepository.deleteAll(postedEntities);
        return postedEntities;
    }
    /**
     * Posts all entities of vdcs that are found and deletes the actually sent entities.
     * @return a list of sent vdcs
     */
    public List<VDC> postAllVDCs(){
        var entities = vdcRepository.findAll();
        var postedEntities = new ArrayList<VDC>();
        for (var entity: entities) {
            try {
            postedEntities.add(webClient.post()
                    .uri("/vdcs")
                    .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                    .body(Mono.just(entity), VDC.class)
                    .retrieve()
                    .bodyToMono(VDC.class)
                    .block());
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        vdcRepository.deleteAll(postedEntities);
        return postedEntities;
    }
    /**
     * Posts all entities of networks that are found and deletes the actually sent entities.
     * @return a list of sent networks
     */
    public List<OrgNetwork> postAllNetworks(){
        var entities= orgNetworkRepository.findAll();
        var postedEntities = new ArrayList<OrgNetwork>();
        for (var entity : entities) {
            try {
                postedEntities.add(webClient.post()
                        .uri("/org-networks")
                        .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                        .body(Mono.just(entity), OrgNetwork.class)
                        .retrieve()
                        .bodyToMono(OrgNetwork.class)
                        .block());
                }
                catch (Exception e) {
                logger.error(e.getMessage());
                }
        }
        orgNetworkRepository.deleteAll(postedEntities);
        return postedEntities;
    }
    /**
     * Posts all entities of compute capacities that are found and deletes the actually sent entities.
     * @return a list of sent compute capacities
     */
    public List<VDCComputeCapacity> postAllComputeCapacities(){
        var entities = computeCapacityRepository.findAll();
        var postedEntities = new ArrayList<VDCComputeCapacity>();
        for (var entity : entities) {
            try {
                postedEntities.add(webClient.post()
                        .uri("/vdc-compute-capacities")
                        .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                        .body(Mono.just(entity ), VDCComputeCapacity.class)
                        .retrieve()
                        .bodyToMono(VDCComputeCapacity.class)
                        .block());
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        computeCapacityRepository.deleteAll(postedEntities);
        return postedEntities;
    }
    /**
     * Posts all entities of storage profiles that are found and deletes the actually sent entities.
     * @return a list of sent storage profiles
     */
    public List<VDCStorageProfile> postAllStorageProfiles(){
        var entities = storageProfileRepository.findAll();
        var postedEntities= new ArrayList<VDCStorageProfile>();
        for (var entity : entities) {
            try {
                postedEntities.add(webClient.post()
                        .uri("/vdc-storage-profiles")
                        .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                        .body(Mono.just(entity ), VDCStorageProfile.class)
                        .retrieve()
                        .bodyToMono(VDCStorageProfile.class)
                        .block());
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        storageProfileRepository.deleteAll(postedEntities);
        return postedEntities;
    }
    /**
     * Posts all entities of vapps that are found and deletes the actually sent entities.
     * @return a list of sent vapps
     */
    public List<VApp> postAllVApps(){
        var entities = vAppRepository.findAll();
        var postedEntities = new ArrayList<VApp>();
        for (var entity: entities) {
            try {
                postedEntities.add(webClient.post()
                        .uri("/vapps")
                        .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                        .body(Mono.just(entity), VApp.class)
                        .retrieve()
                        .bodyToMono(VApp.class)
                        .block());
            }
            catch (Exception e){
                logger.error(e.getMessage());
            }
        }
        vAppRepository.deleteAll(postedEntities);
        return postedEntities;
    }

    /**
     * Posts all entities of apps that are found and deletes the actually sent entities.
     * @return a list of sent apps
     */
    public List<App> postAllApps(){
        var entities = appRepository.findAll();
        var postedEntities = new ArrayList<App>();
        for (var entity : entities) {
            try {
                postedEntities.add(webClient.post()
                        .uri("/apps")
                        .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                        .body(Mono.just(entity), App.class)
                        .retrieve()
                        .bodyToMono(App.class)
                        .block());
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        appRepository.deleteAll(postedEntities);
        return postedEntities;
    }

    /**
     * Posts all entities of osbs that are found and deletes the actually sent entities.
     * @return a list of sent osbs
     */
    public List<OSB> postAllOSBs(){
        var entities = osbRepository.findAll();
        var postedEntities = new ArrayList<OSB>();
        for (var entity : entities) {
            try {
                postedEntities.add(webClient.post()
                        .uri("/osbs")
                        .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                        .body(Mono.just(entity), OSB.class)
                        .retrieve()
                        .bodyToMono(OSB.class)
                        .block());
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        osbRepository.deleteAll(postedEntities);
        return postedEntities;
    }

    /**
     * Posts all entities of spaces that are found and deletes the actually sent entities.
     * @return a list of sent spaces
     */
    public List<Space> postAllSpaces(){
        var entities = spaceRepository.findAll();
        var postedEntities = new ArrayList<Space>();
        for (var entity : entities) {
            try {
                postedEntities.add(webClient.post()
                        .uri("/spaces")
                        .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                        .body(Mono.just(entity), Space.class)
                        .retrieve()
                        .bodyToMono(Space.class)
                        .block());
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        spaceRepository.deleteAll(postedEntities);
        return postedEntities;
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
        postAllSpaces();
        postAllOSBs();
        postAllApps();
    }
}
