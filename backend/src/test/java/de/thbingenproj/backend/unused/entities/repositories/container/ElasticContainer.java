package de.thbingenproj.backend.unused.entities.repositories.container;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
/**
 * Container for creation of the testcontainer for elastic-search
 */
class ElasticContainer {
    @Container
    private static final ElasticsearchContainer elasticsearchContainer = new ElasticTest();

    @BeforeAll
    static void setUp() {
        elasticsearchContainer.start();
    }

    @AfterAll
    static void destroy() {
        elasticsearchContainer.stop();
    }
}
