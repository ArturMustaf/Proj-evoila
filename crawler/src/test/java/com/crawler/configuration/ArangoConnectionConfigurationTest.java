package com.crawler.configuration;

import com.crawler.extensions.PostgresTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@DisabledOnOs(OS.LINUX)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresTest.PropertiesInitializer.class})
class ArangoConnectionConfigurationTest {

    @Autowired
    ArangoConnectionConfiguration arangoConf;

    @Test
    void arangoBuilderTest() {
        Assertions.assertNotNull(arangoConf.arango());
    }

    @Test
    void databasePrintNameTest() {
        Assertions.assertSame("prom",arangoConf.database());
    }
}