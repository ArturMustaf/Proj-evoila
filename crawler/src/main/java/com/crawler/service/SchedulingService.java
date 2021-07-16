package com.crawler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Class to schedule the retrieval and sending of data
 */
@Configuration
@EnableScheduling
public class SchedulingService {
    @Autowired
    APIClient apiClient;
    @Autowired
    SendClient sendClient;
    @Autowired
    ArangoService arangoService;

    final Logger logger = LoggerFactory.getLogger(SchedulingService.class);

    /**
     * <p>1. Pulls all wanted Data from vCloud Director API and from ArangoDB</p>
     * <p>2. Sends all stored data to backend</p>
     */
    @Scheduled(cron = "1 1 * * * *")
    public void pullAndSendAllData(){
        logger.info("Starting to pull vCloud data");
        logger.info("Starting to pull vCloud data");
        apiClient.getAllOrgs();
        logger.info("Successfully pulled all vCloud data");
        logger.info("Starting to pull cloud foundry data");
        arangoService.getAllArangoData();
        logger.info("Successfully pulled all cloud foundry data");
        logger.info("Started sending all data to backend");
        sendClient.sendAll();
        logger.info("finished sending");
    }
}
