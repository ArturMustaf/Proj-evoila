package com.crawler;

import com.crawler.arango.entity.OSB;
import com.crawler.arango.entity.Space;
import com.crawler.arango.repository.AppRepository;
import com.crawler.arango.repository.OSBRepository;
import com.crawler.arango.repository.SpaceRepository;
import com.crawler.extensions.PostgresTest;
import com.crawler.postgres.entity.PostgresApp;
import com.crawler.postgres.entity.PostgresOSB;
import com.crawler.postgres.entity.PostgresSpace;
import com.crawler.postgres.repository.PostgresAppRepository;
import com.crawler.postgres.repository.PostgresOSBRepository;
import com.crawler.postgres.repository.PostgresSpaceRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;
import java.util.ArrayList;

@SpringBootTest
@ComponentScan("com.crawler.configuration")
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
class ArangoTest {
    @Autowired
    OSBRepository arangoOSBRepository;
    @Autowired
    AppRepository arangoAppRepository;
    @Autowired
    SpaceRepository arangoSpaceRepository;
    @Autowired
    PostgresOSBRepository postgresOSBRepository;
    @Autowired
    PostgresAppRepository postgresAppRepository;
    @Autowired
    PostgresSpaceRepository postgresSpaceRepository;
    @Autowired
    ModelMapper modelMapper;

    @Test
    @Transactional
    void testSpaceRetrieval(){
        var entities = new ArrayList<PostgresSpace>();
        for(Space space: arangoSpaceRepository.findAll()){
            entities.add(modelMapper.map(space, PostgresSpace.class));
        }
        postgresSpaceRepository.saveAll(entities);
        System.out.println("OSBS: "+ postgresSpaceRepository.findAll());
    }

    @Test
    @Transactional
    void testOSBRetrieval(){
        var entities = new ArrayList<PostgresOSB>();
        for(OSB osb: arangoOSBRepository.findAll()){
            entities.add(modelMapper.map(osb, PostgresOSB.class));
        }
        postgresOSBRepository.saveAll(entities);
        System.out.println("OSBS: "+ postgresOSBRepository.findAll());
    }

    @Test
    @Transactional
    void testAppRetrieval(){
        var entities = new ArrayList<PostgresApp>();
        for(OSB osb: arangoOSBRepository.findAll()){
            entities.add(modelMapper.map(osb, PostgresApp.class));
        }
        postgresAppRepository.saveAll(entities);
        System.out.println("OSBS: "+ postgresAppRepository.findAll());
    }
}
