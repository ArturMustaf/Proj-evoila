package com.basecrawler.service;

import com.basecrawler.entity.*;
import com.basecrawler.model.OrgUserRoleDTO;
import com.basecrawler.model.VDCDTO;
import com.basecrawler.record.OrgNetworkRecord;
import com.basecrawler.record.OrgRecord;
import com.basecrawler.model.OrgUserDTO;
import com.basecrawler.model.OrganizationDTO;
import com.basecrawler.record.OrgUserRecord;
import com.basecrawler.repository.OrgUserRepository;
import com.basecrawler.repository.OrgUserRoleRepository;
import com.basecrawler.repository.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

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
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath / resources");
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


    public OrgRecord getAllOrgs(){
        var entity = client.get().uri("/query?type=organization")
                .retrieve().bodyToMono(OrgRecord.class)
                .block();

        if(entity != null){
            for(OrganizationDTO organizationDTO : entity.getRecord()){
                var org = modelMapper.map(getOneOrgByID(getID(organizationDTO.getHref(), "org")), Organization.class);
                organizationRepository.save(org);
                getRolesByOrgID(org.getId());
                getOrgNetworksByOrgID(org.getId());
                getOrgUsersByOrgID(org.getId());
                getOrgVDCsByOrgID(org.getId());
                getOrgVAppsByOrgID(org.getId());
            }
        }
        return entity;
    }

    public OrganizationDTO getOneOrgByID(String orgID){
        var org = client.get().uri("/admin/org/" +orgID)
                .retrieve().bodyToMono(OrganizationDTO.class)
                .block();
        org.setId(org.getId().replace("urn:vcloud:org:", ""));
        return org;
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
            var orgUser= modelMapper.map(user, OrgUser.class);
            orgUser.setOrgID(orgID);
            orgUser.setId(getID(user.getHref(), "admin/user"));
            orgUser.setRoleID(getOneOrgUserByID(orgUser.getId()).getRole().getId().replace("urn:vcloud:role:", ""));
            entities.add(orgUser);
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
        for(VDCDTO vdcDTO : org.getVdcs().getRecord()){
            var vdc = modelMapper.map(vdcDTO, VDC.class);

        }

        return entities;
    }

    public VDCDTO getOneVDCByID(String vdcID){
        return client.get().uri("/vdc/"+vdcID)
                .retrieve().bodyToMono(VDCDTO.class)
                .block();
    }

    public ArrayList<OrgNetwork> getOrgNetworksByOrgID(String orgID){

        return null;
    }

    public ArrayList<VApp> getOrgVAppsByOrgID(String orgID){

        return null;
    }

    public String getID(String input, String type){
        return input.replace("https://vcloud.evoila.io/api/"+type+"/", "");
    }
}
