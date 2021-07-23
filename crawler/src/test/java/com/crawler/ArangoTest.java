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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

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
    void testCreation(){
        Assertions.assertNotNull(arangoAppRepository);
        Assertions.assertNotNull(arangoService);
        Assertions.assertNotNull(arangoOSBRepository);
        Assertions.assertNotNull(arangoSpaceRepository);
        Assertions.assertNotNull(postgresAppRepository);
        Assertions.assertNotNull(postgresOSBRepository);
        Assertions.assertNotNull(postgresSpaceRepository);
        Assertions.assertNotNull(modelMapper);
    }

}
