package com.crawler.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.concurrent.TimeoutException;

import static java.lang.Thread.sleep;

public class APIAuthService {
    int retries = 0;

    final WebClient client = WebClient.builder()
            .baseUrl("https://vcloud.evoila.io/api")
            .defaultHeader("Accept", "application/*+JSON;version=34.0")
            .build();


    //TODO log for SOS-39
    public String getBearerToken(String username, String password) throws InterruptedException, TimeoutException {
        try {
            ResponseEntity<String> entity = client.post().uri("/sessions")
                    .headers(headers -> headers.setBasicAuth(username, password))
                    .retrieve()
                    .toEntity(String.class)
                    .block();

            if(entity != null){
                return entity.getHeaders().getFirst("X-VMWARE-VCLOUD-ACCESS-TOKEN");
            }

        } catch (WebClientResponseException e) {
            switch (e.getStatusCode()) {
                case GATEWAY_TIMEOUT:
                case REQUEST_TIMEOUT:
                    sleep(1000);
                    retries++;
                    getBearerToken(username, password);
                    break;
                default:
                    throw e;
            }
            if(retries > 5){
               throw new TimeoutException("Maximum number of failed login attempts exceeded. Server might be unreachable.");
            }
        }
        return "";
    }

}
