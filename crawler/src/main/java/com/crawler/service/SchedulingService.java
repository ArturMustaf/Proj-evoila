package com.crawler.service;

import com.crawler.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class SchedulingService {
    //TODO add ArangoDB Bean
    @Autowired
    APIClient apiClient;
    @Autowired
    SendClient sendClient;
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
    Logger logger = LoggerFactory.getLogger(SchedulingService.class);
    /**
     * Pulls all wanted Data from vCloud Director API and from ArangoDB
     */
    @Scheduled(fixedRate = 30000)
    public void pullAllData(){
        //TODO add ArangoDB calls
        try{
            apiClient.getAllOrgs();
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
        logger.info("Successfully pulled all data");
    }

    /**
     * Tries to send all data to backend
     * Deletes actually send data to safe space
     * If sending fails keeps data and retries sending them on next call
     */
    @Scheduled(fixedRate = 30000)
    public void sendAllData(){
        //TODO send arango data
        logger.info("Started sending all data to backend");
        try {
            sendClient.sendAll();
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("finished sending");
    }

}
