package com.crawler;

import com.crawler.arango.repository.ArangoAppRepository;
import com.crawler.arango.repository.ArangoOSBRepository;
import com.crawler.arango.repository.ArangoSpaceRepository;
import com.crawler.extensions.PostgresTest;
import com.crawler.postgres.repository.AppRepository;
import com.crawler.postgres.repository.OSBRepository;
import com.crawler.postgres.repository.SpaceRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;

@SpringBootTest
@ComponentScan("com.crawler.configuration")
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
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

    @Test
    @Transactional
    void testSpaceRetrieval(){
    }

    @Test
    @Transactional
    void testOSBRetrieval(){
    }

    @Test
    @Transactional
    void testAppRetrieval(){

    }
}
