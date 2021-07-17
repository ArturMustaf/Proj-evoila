package com.crawler.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;

/**
 * Service to get an bearer token for communication between different clients that a secured via keycloak
 */

@PropertySource("classpath:logindata.properties")
@Service
public class KeycloakAuthService {
    @Autowired
    private Environment env;
    @Value("${keycloak.tokenURI}")
    private String baseURL;
    /**
     * Keycloak url: http://hatkeinehaende.de:9080
     * Path to receive access token: /auth/realms/TH-Bingen/protocol/openid-connect/token
     */
    WebClient webClient = WebClient.builder()
            .baseUrl(baseURL)
            .build();

    @PostConstruct
    void initWebclient(){
        webClient = WebClient.builder()
                .baseUrl(baseURL)
                .build();
    }

    final Logger logger = LoggerFactory.getLogger(KeycloakAuthService.class);

    /**
     * posts to keycloak and retrieves an access token for the used client
     * @return access_token
     * <code>setProps()</code> need to be called before to read the properties from file
     */
    public String getKeycloakBearerToken(){
        try{
            var mapper = new ObjectMapper();
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("grant_type", env.getProperty("keycloak.grantType"));
            formData.add("client_secret", env.getProperty("keycloak.clientSecret"));
            formData.add("client_id", env.getProperty("keycloak.clientID"));
            var response = webClient
                    .post()
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .toEntity(String.class)
                    .block();

            if(response != null){
                JsonNode root = mapper.readTree(response.getBody());
                JsonNode accessToken = root.path("access_token");
                return accessToken.toString().replace("\"", "");
            } else {
                logger.warn("Returned access token was empty");
                return "";
            }
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return "";
        }
    }
}
