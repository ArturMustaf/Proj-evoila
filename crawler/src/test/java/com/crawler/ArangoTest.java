package com.crawler;

import com.crawler.arango.repository.ArangoAppRepository;
import com.crawler.arango.repository.ArangoOSBRepository;
import com.crawler.arango.repository.ArangoSpaceRepository;
import com.crawler.extensions.PostgresTest;
import com.crawler.postgres.repository.AppRepository;
import com.crawler.postgres.repository.OSBRepository;
import com.crawler.postgres.repository.SpaceRepository;
import com.crawler.service.ArangoService;
import com.crawler.service.SchedulingService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;

@SpringBootTest
@ComponentScan("com.crawler.configuration")
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
@MockBean(SchedulingService.class)
class ArangoTest {
    @Autowired
    ArangoOSBRepository arangoOSBRepository;
    @Autowired
    ArangoAppRepository arangoAppRepository;
    @Autowired
    ArangoSpaceRepository arangoSpaceRepository;
    @Autowired
    OSBRepository postgresOSBRepository;
    @Autowired
    AppRepository postgresAppRepository;
    @Autowired
    SpaceRepository postgresSpaceRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ArangoService arangoService;

    @Test
    @Transactional
    void testSpaceRetrieval(){
        arangoService.retrieveAllSpaces();
        System.out.println(postgresSpaceRepository.findAll());
    }

    @Test
    @Transactional
    void testOSBRetrieval(){
        arangoService.retrieveAllOSBsFromDate(0);
        System.out.println(postgresOSBRepository.findAll());
    }

    @Test
    @Transactional
    void testAppRetrieval(){
        arangoService.retrieveAllAppsFromDate(0);
        System.out.println(postgresAppRepository.findAll());
    }
}
