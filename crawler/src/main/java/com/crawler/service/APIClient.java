package com.crawler.service;

import com.crawler.postgres.entity.*;
import com.crawler.postgres.model.*;
import com.crawler.postgres.record.OrgRecord;
import com.crawler.postgres.record.VAppRecord;
import com.crawler.postgres.repository.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@PropertySource("classpath:logindata.properties")
@Service
public class APIClient {
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    ModelMapper modelMapper;
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
    private Environment env;
    @Autowired
    APIAuthService authService;
    @Value("${api.baseURL}")
    private String baseURL;

    final Logger logger = LoggerFactory.getLogger(APIClient.class);

    WebClient client;

    @PostConstruct
    void initWebclient(){
        client = WebClient.builder()
                .baseUrl(baseURL)
                .defaultHeader("Accept", "application/*+JSON;version=34.0")
                .build();
    }
    /**
     * Builds a hierarchical structure of all orgs and data the entail
     * @return true if succeeded
     */
    public boolean getAllOrgs(){
        try {
            var entity = client.get().uri("/query?type=organization")
                    .headers(headers -> headers.setBearerAuth(authService.getBearerToken()))
                    .retrieve().bodyToMono(OrgRecord.class)
                    .block();

            if(entity != null){
                var organizations = new ArrayList<Organization>();
                for(OrganizationDTO organizationDTO : entity.getOrganizationRecord()){
                    var org = modelMapper.map(getOneOrgByID(getID(organizationDTO.getHref(), "org")), Organization.class);
                    organizations.add(org);
                    getOneOrgByID(org.getId());
                    getRolesByOrgID(org.getId());
                    getOrgUsersByOrgID(org.getId());
                    getOrgVDCsByOrgID(org.getId());
                    getAllVApps();
                }
                organizationRepository.saveAll(organizations);
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * Gets one org and sets its ID
     * @param orgID used to declare the org searched for
     * @return DTO object of given org
     */
    public OrganizationDTO getOneOrgByID(String orgID){
        var organizationDTO = client.get().uri("/admin/org/" +orgID)
                .headers(headers -> headers.setBearerAuth(authService.getBearerToken()))
                .retrieve().bodyToMono(OrganizationDTO.class)
                .block();
        if(organizationDTO != null){
            organizationDTO.setId(organizationDTO.getId().replace("urn:vcloud:org:", ""));
        }
        return organizationDTO;
    }

    /**
     * Gets all userRoles of a given org
     * @param orgId used to declare the org searched for
     * @return list of all userRoles of specified org
     */
    public List<OrgUserRole> getRolesByOrgID(String orgId){
        var org = getOneOrgByID(orgId);
        var entities = new ArrayList<OrgUserRole>();
        for(OrgUserRoleDTO role : org.getRoleReferences().getOrgUserRoleRecord()){
            var orgUserRole = modelMapper.map(role, OrgUserRole.class);
            orgUserRole.setId(getID(role.getHref(), "admin/org/"+orgId+"/role"));
            orgUserRole.setOrgID(orgId);
            entities.add(orgUserRole);
        }
        orgUserRoleRepository.saveAll(entities);
        return entities;
    }

    /**
     * Gets all users of a given org
     * @param orgID used to declare the org searched for
     * @return list of all users of specified org
     */
    public List<OrgUser> getOrgUsersByOrgID(String orgID){
        var org = getOneOrgByID(orgID);
        var entities = new ArrayList<OrgUser>();
        for(OrgUserDTO user: org.getUsers().getOrgUserRecords()){
            var orgUser= getOneOrgUserByID(getID(user.getHref(), "admin/user"));
            orgUser.setId(getID(user.getHref(), "admin/user"));
            orgUser.setOrgID(orgID);
            orgUser.setOrgRoleID(orgUser.getRole().getId().replace("urn:vcloud:role:", ""));
            entities.add(modelMapper.map(orgUser, OrgUser.class));
        }
        orgUserRepository.saveAll(entities);
        return entities;
    }

    /**
     * Gets all data of a given user
     * @param orgUserID id of the user
     * @return DTO of the user
     */
    public OrgUserDTO getOneOrgUserByID(String orgUserID){
        return client.get().uri("/admin/user/" +orgUserID)
                .headers(headers -> headers.setBearerAuth(authService.getBearerToken()))
                .retrieve().bodyToMono(OrgUserDTO.class)
                .block();
    }

    /**
     * Gets all associated data for all vdcs of a given org
     * @param orgID id of the org
     * @return list of all vdcs
     */
    public List<VDC> getOrgVDCsByOrgID(String orgID){
        var org = getOneOrgByID(orgID);
        var entities = new ArrayList<VDC>();
        var computeCapacities = new ArrayList<VDCComputeCapacity>();
        var storageProfiles = new ArrayList<VDCStorageProfile>();
        var vdcNetworks = new ArrayList<OrgNetwork>();
        for(VDCDTO vdcDTO : org.getVdcs().getVdc()){
            var vdc = getOneVDCByID(getID(vdcDTO.getHref(), "vdc"));
            vdc.setId(vdc.getId().replace("urn:vcloud:vdc:", ""));
            vdc.setOrgID(orgID);
            var computeCapacity = vdc.getComputeCapacity();
            computeCapacity.setDate(System.currentTimeMillis());
            computeCapacities.add(modelMapper.map(computeCapacity, VDCComputeCapacity.class));
            var vdcStorageProfiles = vdc.getVdcStorageProfiles().getVdcStorageProfile();
            for (VDCStorageProfileDTO storageProfileDTO : vdcStorageProfiles){
                var storageProfile = modelMapper.map(getOneStorageProfileByID(getID(storageProfileDTO.getHref(),"vdcStorageProfile" )), VDCStorageProfile.class);
                storageProfile.setId(storageProfile.getId().replace("urn:vcloud:vdcstorageProfile:", ""));
                storageProfile.setDate(System.currentTimeMillis());
                storageProfile.setVdcID(vdc.getId());
                storageProfiles.add(storageProfile);
            }
            var networkRecord = vdc.getAvailableNetworks().getNetwork();
            for(OrgNetworkDTO networkDTO : networkRecord){
                var network = modelMapper.map(networkDTO, OrgNetwork.class);
                network.setId(network.getId().replace("urn:vcloud:network:" , ""));
                network.setOrgID(orgID);
                network.setVdcID(vdc.getId());
                vdcNetworks.add(network);
            }
            entities.add(modelMapper.map(vdc, VDC.class));
        }
        orgNetworkRepository.saveAll(vdcNetworks);
        storageProfileRepository.saveAll(storageProfiles);
        vdcRepository.saveAll(entities);
        computeCapacityRepository.saveAll(computeCapacities);
        return entities;
    }

    /**
     * Gets all data of a single VDC
     * @param vdcID id of the vdc
     * @return DTO of the specified VDC
     */
    public VDCDTO getOneVDCByID(String vdcID){
        return client.get().uri("/vdc/"+vdcID)
                .headers(headers -> headers.setBearerAuth(authService.getBearerToken()))
                .retrieve().bodyToMono(VDCDTO.class)
                .block();
    }

    /**
     * Gets all data of a single storage profile
     * @param storageID if of the storage profile
     * @return DTO of the storage profile
     */
    public VDCStorageProfileDTO getOneStorageProfileByID(String storageID){
        return client.get().uri("/vdcStorageProfile/"+storageID)
                .headers(headers -> headers.setBearerAuth(authService.getBearerToken()))
                .retrieve().bodyToMono(VDCStorageProfileDTO.class)
                .block();
    }

    /**
     * Gets all vApps
     * @return record of all vApps
     */
    public VAppRecord getAllVApps(){
        var vApps = client.get().uri("/vApps/query")
                .headers(headers -> headers.setBearerAuth(authService.getBearerToken()))
                .retrieve().bodyToMono(VAppRecord.class)
                .block();
        var entities = new ArrayList<VApp>();
        if(vApps != null){
            for(VAppDTO vAppDTO : vApps.getVAppsRecord()){
                var vApp = modelMapper.map(vAppDTO, VApp.class);
                vApp.setId(vApp.getHref().replace("https://vcloud.evoila.io/api/vApp/vapp-",""));
                vApp.setVdcID(getID(vApp.getVdcHRef(), "vdc"));
                entities.add(vApp);
            }
        }
        vAppRepository.saveAll(entities);
        return vApps;
    }

    /**
     * Crops a id string
     * @param input
     * @param type
     * @return ID
     */
    public String getID(String input, String type){
        return input.replace("https://vcloud.evoila.io/api/"+type+"/", "");
    }
}
