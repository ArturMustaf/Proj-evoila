package com.crawler.service;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * Service to get an bearer token for communication between different clients that a secured via keycloak
 */
@Service
public class KeycloakAuthService {
    /**
     * Keycloak url: http://hatkeinehaende.de:9080
     * Path to receive access token: /auth/realms/TH-Bingen/protocol/openid-connect/token
     */
    WebClient webClient = WebClient.builder()
            .baseUrl("http://hatkeinehaende.de:9080/auth/realms/TH-Bingen/protocol/openid-connect/token")
            .build();

    /**
     * posts to keycloak and retrieves an access token for the used client
     * @return access_token
     * <code>setProps()</code> need to be called before to read the properties from file
     */
    public String getKeycloakBearerToken(){
        try{
            var propFileName = "logindata.properties";
            var prop = new Properties();
            var inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) prop.load(inputStream);
            else {
                throw new FileNotFoundException("property file '"
                        + propFileName +
                        "' not found in the classpath / resources. Please create the file and set clientID, clientSecret and grantType.");
            }

            var mapper = new ObjectMapper();
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("grant_type", prop.getProperty("grant_type"));
            formData.add("client_secret", prop.getProperty("clientSecret"));
            formData.add("client_id", prop.getProperty("clientID"));
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
                return accessToken.toString();
            } else {
                return "";
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
