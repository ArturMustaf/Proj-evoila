package com.basecrawler.service;

import com.basecrawler.model.OrgRecord;
import com.basecrawler.model.Organisation;
import com.basecrawler.model.VDCRecord;
import org.springframework.web.reactive.function.client.WebClient;

public class APIClient {
    static final APIAuthService authService = new APIAuthService();
    final WebClient client = WebClient.builder()
            .baseUrl("https://vcloud.evoila.io/api")
            .defaultHeader("Accept", "application/*+JSON;version=34.0")
            .defaultHeaders(headers -> headers.setBearerAuth(authService.getBearerToken("philipp.grafmueller@th-bingen.de@ORG-TU_DA", "PPZ8LHTpI4k2Ij1s")))
            .build();

    public OrgRecord getAllOrgs(){
        var entity = client.get().uri("/query?type=organization")
                .retrieve().bodyToMono(OrgRecord.class)
                .block();

        if(entity != null){
            for(var org : entity.getRecord()){
                org.setId(org.getHref().replace("https://vcloud.evoila.io/api/org/", ""));
                System.out.println(org);
            }
        }
        return entity;
    }

    public Organisation getOneOrg(String id){
        var org = client.get().uri("/org/" +id)
                .retrieve().bodyToMono(Organisation.class)
                .block();
        org.setId(org.getId().replace("urn:vcloud:org:", ""));
        return org;
    }

    public VDCRecord getAllVDCs(){
        var vdcRecord = client.get().uri("/admin/vdcs/query")
                .retrieve().bodyToMono(VDCRecord.class)
                .block();
        return vdcRecord;
    }
}
