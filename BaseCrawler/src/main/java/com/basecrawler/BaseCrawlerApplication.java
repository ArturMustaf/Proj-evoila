package com.basecrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BaseCrawlerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseCrawlerApplication.class, args);

        var orgs = apiClient.getAllOrgs();
        if(orgs != null) {
            for(var org : orgs.getRecord()) {
                System.out.println(apiClient.getOneOrg(org.getId()));
                apiClient.getMetric(org.getId());
            }
        }

        var vdcs = apiClient.getAllVDCs();
        System.out.println(vdcs);
    }

}