package com.crawler.service;

import com.crawler.entity.*;
import com.crawler.model.*;
import com.crawler.record.OrgRecord;
import com.crawler.record.VAppRecord;
import com.crawler.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

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

    static final APIAuthService authService = new APIAuthService();
    String username;
    String password;

    public void getProps() throws IOException {
        String propFileName = "logindata.properties";
        Properties prop = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath / resources. Please create the file and set user and password.");
        }

        // get the property value and print it out
        username = prop.getProperty("user");
        password = prop.getProperty("password");

    }

    final WebClient client = WebClient.builder()
            .baseUrl("https://vcloud.evoila.io/api")
                .defaultHeader("Accept", "application/*+JSON;version=34.0")
                .defaultHeaders(headers -> {
                    try {
                        getProps();
                        headers.setBearerAuth(authService.getBearerToken(username, password));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                 })
            .build();

    public boolean getAllOrgs(){
        try {
            var entity = client.get().uri("/query?type=organization")
                    .retrieve().bodyToMono(OrgRecord.class)
                    .block();

            if(entity != null){
                var organizations = new ArrayList<Organization>();

                for(OrganizationDTO organizationDTO : entity.getRecord()){
                    var org = modelMapper.map(getOneOrgByID(getID(organizationDTO.getHref(), "org")), Organization.class);
                    organizations.add(org);
                    getOneOrgByID(org.getId());
                    getRolesByOrgID(org.getId());
//                getOrgNetworksByOrgID(org.getId());
                    getOrgUsersByOrgID(org.getId());
                    getOrgVDCsByOrgID(org.getId());
                    getAllVApps();
                }
                organizationRepository.saveAll(organizations);
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public OrganizationDTO getOneOrgByID(String orgID){
        var organizationDTO = client.get().uri("/admin/org/" +orgID)
                .retrieve().bodyToMono(OrganizationDTO.class)
                .block();
        organizationDTO.setId(organizationDTO.getId().replace("urn:vcloud:org:", ""));
        return organizationDTO;
    }

    public ArrayList<OrgUserRole> getRolesByOrgID(String orgId){
        var org = getOneOrgByID(orgId);
        var entities = new ArrayList<OrgUserRole>();
        for(OrgUserRoleDTO role : org.getRoleReferences().getRecord()){
            var orgUserRole = modelMapper.map(role, OrgUserRole.class);
            orgUserRole.setId(getID(role.getHref(), "admin/org/"+orgId+"/role"));
            orgUserRole.setOrgID(orgId);
            entities.add(orgUserRole);
        }
        orgUserRoleRepository.saveAll(entities);
        return entities;
    }

    public ArrayList<OrgUser> getOrgUsersByOrgID(String orgID){
        var org = getOneOrgByID(orgID);
        var entities = new ArrayList<OrgUser>();
        for(OrgUserDTO user: org.getUsers().getRecord()){
            var orgUser= getOneOrgUserByID(getID(user.getHref(), "admin/user"));
            orgUser.setId(getID(user.getHref(), "admin/user"));
            orgUser.setOrgID(orgID);
            orgUser.setOrgRoleID(orgUser.getRole().getId().replace("urn:vcloud:role:", ""));
            entities.add(modelMapper.map(orgUser, OrgUser.class));
        }
        orgUserRepository.saveAll(entities);
        return entities;
    }

    public OrgUserDTO getOneOrgUserByID(String orgUserID){
        return client.get().uri("/admin/user/" +orgUserID)
                .retrieve().bodyToMono(OrgUserDTO.class)
                .block();
    }

    public ArrayList<VDC> getOrgVDCsByOrgID(String orgID){
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

    public VDCDTO getOneVDCByID(String vdcID){
        return client.get().uri("/vdc/"+vdcID)
                .retrieve().bodyToMono(VDCDTO.class)
                .block();
    }

    public VDCStorageProfileDTO getOneStorageProfileByID(String storageID){
        return client.get().uri("/vdcStorageProfile/"+storageID)
                .retrieve().bodyToMono(VDCStorageProfileDTO.class)
                .block();
    }


    public VAppRecord getAllVApps(){
        var vApps = client.get().uri("/vApps/query")
                .retrieve().bodyToMono(VAppRecord.class)
                .block();
        var entities = new ArrayList<VApp>();
        for(VAppDTO vAppDTO : vApps.getRecord()){
            var vApp = modelMapper.map(vAppDTO, VApp.class);
            vApp.setId(vApp.getHref().replace("https://vcloud.evoila.io/api/vApp/vapp-",""));
            vApp.setVdcID(getID(vApp.getVdcHRef(), "vdc"));
            entities.add(vApp);
        }
        vAppRepository.saveAll(entities);
        return vApps;
    }

    public String getID(String input, String type){
        return input.replace("https://vcloud.evoila.io/api/"+type+"/", "");
    }
}
