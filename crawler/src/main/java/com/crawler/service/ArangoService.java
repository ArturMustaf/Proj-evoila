package com.crawler.service;

import com.crawler.arango.entity.ArangoApp;
import com.crawler.arango.entity.ArangoOSB;
import com.crawler.arango.repository.ArangoAppRepository;
import com.crawler.arango.repository.ArangoOSBRepository;
import com.crawler.arango.repository.ArangoSpaceRepository;
import com.crawler.postgres.entity.App;
import com.crawler.postgres.entity.OSB;
import com.crawler.postgres.entity.Space;
import com.crawler.postgres.repository.AppRepository;
import com.crawler.postgres.repository.OSBRepository;
import com.crawler.postgres.repository.SpaceRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@PropertySource("classpath:logindata.properties")
@Service
public class ArangoService {
    @Autowired
    ArangoSpaceRepository arangoSpaceRepository;
    @Autowired
    ArangoAppRepository arangoAppRepository;
    @Autowired
    ArangoOSBRepository arangoOSBRepository;
    @Autowired
    AppRepository postgresAppRepository;
    @Autowired
    SpaceRepository postgresSpaceRepository;
    @Autowired
    OSBRepository postgresOSBRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    KeycloakAuthService authService;
    @Autowired
    private Environment env;
    @Value("${backend.baseURL}")
    private String baseURL;
    final Logger logger = LoggerFactory.getLogger(ArangoService.class);

    WebClient webClient;

    @PostConstruct
    void initWebclient(){
        webClient  = WebClient.builder()
                .baseUrl(baseURL)
                .defaultHeader(MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    /**
     * Calls all functions to retrieve data
     */
    public void getAllArangoData(){
        var lastUpdate = getLastUpdate();
        retrieveAllSpaces();
        retrieveAllOSBsFromDate(lastUpdate);
        retrieveAllAppsFromDate(lastUpdate);
    }

    /**
     * Gets the last date of any update in backend
     * @return last update date as long
     */
    public long getLastUpdate(){
        long lastUpdate = 0;
        var response = webClient.get()
                .uri("arango/lastUpdate")
                .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                .retrieve()
                .toEntity(String.class)
                .block();

        if(response != null){
            lastUpdate = Long.parseLong(response.getBody());
        }
        return lastUpdate;
    }

    /**
     * Gets all spaces
     * @return list of all spaces
     */
    public List<Space> retrieveAllSpaces(){
        var entities = new ArrayList<Space>();
        for(var space: arangoSpaceRepository.findAll()){
            entities.add(modelMapper.map(space, Space.class));
        }
        postgresSpaceRepository.saveAll(entities);
        return entities;
    }

    /**
     * Gets all osbs after specified date
     * @param date date of the last update
     * @return list of all osbs
     */
    public List<OSB> retrieveAllOSBsFromDate(long date){
        var entities = new ArrayList<OSB>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        var dateString = dateFormat.format(new Date(date));
        for(var osb: arangoOSBRepository.findAllByDate(dateString)){
            if(modelMapper.getTypeMap(ArangoOSB.class, OSB.class) == null){
                PropertyMap<ArangoOSB, OSB> clientPropertyMap = new PropertyMap<ArangoOSB, OSB>() {
                    @Override
                    protected void configure() {
                        skip(destination.getDt());
                    }
                };
                modelMapper.addMappings(clientPropertyMap);
            }
            var newOSB = modelMapper.map(osb, OSB.class);
            try {
                newOSB.setDt(dateFormat.parse(osb.getDt()).getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
            entities.add(newOSB);
        }
        postgresOSBRepository.saveAll(entities);
        return entities;
    }

    /**
     * Gets all apps after specified date
     * @param date date of last update
     * @return list of all apps
     */
    public List<App> retrieveAllAppsFromDate(long date){
        var entities = new ArrayList<App>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        var dateString = dateFormat.format(new Date(date));
        for(var app: arangoAppRepository.findAllByDate(dateString)){
            if(modelMapper.getTypeMap(ArangoApp.class, App.class) == null){
                PropertyMap<ArangoApp, App> clientPropertyMap = new PropertyMap<ArangoApp, App>() {
                    @Override
                    protected void configure() {
                        skip(destination.getDt());
                    }
                };

                modelMapper.addMappings(clientPropertyMap);
            }
            var newApp = modelMapper.map(app, App.class);
            try {
                newApp.setDt(dateFormat.parse(app.getDt()).getTime());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            entities.add(newApp);
        }
        postgresAppRepository.saveAll(entities);
        return entities;
    }

}
