package com.crawler.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileNotFoundException;
import java.util.Properties;

@Service
public class APIAuthService {

    final WebClient client = WebClient.builder()
            .baseUrl("https://vcloud.evoila.io/api")
            .defaultHeader("Accept", "application/*+JSON;version=34.0")
            .build();


    //TODO log for SOS-39
    public String getBearerToken() {
        try {
            var propFileName = "logindata.properties";
            var prop = new Properties();
            var inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath / resources. Please create the file and set user and password.");
            }
            ResponseEntity<String> entity = client.post().uri("/sessions")
                    .headers(headers -> headers.setBasicAuth(prop.getProperty("user"), prop.getProperty("password")))
                    .retrieve()
                    .toEntity(String.class)
                    .block();

            if(entity != null){
                return entity.getHeaders().getFirst("X-VMWARE-VCLOUD-ACCESS-TOKEN");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

}
