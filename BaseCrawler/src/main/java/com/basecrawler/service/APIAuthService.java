package com.basecrawler.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class APIAuthService {
    final WebClient client = WebClient.builder()
            .baseUrl("https://vcloud.evoila.io/api")
            .defaultHeader("Accept", "application/*+JSON;version=34.0")
            .build();

    public String getBearerToken(String username, String password) throws WebClientResponseException {
        ResponseEntity<String> entity = client.post().uri("/sessions")
                .headers(headers -> headers.setBasicAuth(username, password))
                .retrieve()
                .toEntity(String.class)
                .block();

        if(entity != null){
            return entity.getHeaders().getFirst("X-VMWARE-VCLOUD-ACCESS-TOKEN");
        }
        return "";
    }

}
