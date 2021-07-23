package com.crawler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * Service to connect to vCloudDirector API
 */
@Service
public class APIAuthService {
    @Autowired
    private Environment env;
    @Value("${api.baseURL}")
    private String baseURL;
    WebClient client;

    final Logger logger = LoggerFactory.getLogger(APIAuthService.class);

    @PostConstruct
    void initWebclient(){
        client = WebClient.builder()
                .baseUrl(baseURL)
                .defaultHeader("Accept", "application/*+JSON;version=34.0")
                .build();
    }
    /**
     * Retrieves token from API
     * @return retrieved bearer token as string
     */
    public String getBearerToken() {
        try {
            ResponseEntity<String> entity = client.post().uri("/sessions")
                    .headers(headers -> headers.setBasicAuth(Optional.ofNullable(env.getProperty("api.user")).orElse(""), Optional.ofNullable(env.getProperty("api.password")).orElse("")))
                    .retrieve()
                    .toEntity(String.class)
                    .block();
            if(entity != null){
                return entity.getHeaders().getFirst("X-VMWARE-VCLOUD-ACCESS-TOKEN");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }

}
