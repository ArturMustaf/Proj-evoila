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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    final Logger logger = LoggerFactory.getLogger(ArangoService.class);

    WebClient webClient = WebClient.builder()
            .baseUrl("http://hatkeinehaende.de/")
            .defaultHeader(MediaType.APPLICATION_JSON_VALUE)
            .build();

    public void getAllArangoData(){
        var lastUpdate = getLastUpdate();
        retrieveAllSpaces();
        retrieveAllOSBsFromDate(lastUpdate);
        retrieveAllAppsFromDate(lastUpdate);
    }

    public long getLastUpdate(){
        long lastUpdate = 0;
        var response = webClient.get()
                .uri("arango/lastUpdate")
                .headers(h -> h.setBearerAuth(authService.getKeycloakBearerToken()))
                .retrieve()
                .toEntity(String.class)
                .block();

        if(response != null){
            try{
                var mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.getBody());
                JsonNode update = root.path("lastUpdate");
                lastUpdate = Long.parseLong(update.toString());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return lastUpdate;
    }

    public List<Space> retrieveAllSpaces(){
        var entities = new ArrayList<Space>();
        for(var space: arangoSpaceRepository.findAll()){
            entities.add(modelMapper.map(space, Space.class));
        }
        postgresSpaceRepository.saveAll(entities);
        return entities;
    }

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
