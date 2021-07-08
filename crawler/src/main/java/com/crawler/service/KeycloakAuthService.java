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
import java.io.IOException;
import java.util.Properties;

/**
 * Service to get an bearer token for communication between different clients that a secured via keycloak
 */
@Service
public class KeycloakAuthService {
    String clientID;
    String clientSecret;
    String grantType;

    /**
     * pulls the needed strings from the logindata.properties file
     * @throws IOException
     */
    public void setProps() throws IOException {
        var propFileName = "logindata.properties";
        var prop = new Properties();
        var inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) prop.load(inputStream);
        else {
            throw new FileNotFoundException("property file '"
                    + propFileName +
                    "' not found in the classpath / resources. Please create the file and set clientID, clientSecret and grantType.");
        }

        // get the property value and print it out
        clientID = prop.getProperty("clientID");
        clientSecret = prop.getProperty("clientSecret");
        grantType = prop.getProperty("grantType");
    }

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
            ObjectMapper mapper = new ObjectMapper();
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("grant_type",grantType);
            formData.add("client_secret", clientSecret);
            formData.add("client_id", clientID);
            var response = webClient
                    .post()
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .toEntity(String.class)
                    .block();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode access_token = root.path("access_token");
            return access_token.toString();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return "";
        }
    }
}
