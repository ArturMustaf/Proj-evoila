package de.thbingenproj.backend.entities.repositories.container;

import org.testcontainers.elasticsearch.ElasticsearchContainer;

/**
 * Disclaimer: Does not run while main-program is running through ip conflict
 * Definition of the Elastic-Container and the version which should be pulled from docker
 */
public class ElasticTest extends ElasticsearchContainer {

    private static final String ELASTIC_SEARCH_DOCKER = "docker.elastic.co/elasticsearch/elasticsearch:6.8.13";

    private static final String CLUSTER_NAME = "clustername";

    private static final String ELASTIC_SEARCH = "elasticsearch";

    public ElasticTest() {
        super(ELASTIC_SEARCH_DOCKER);
        this.addFixedExposedPort(9210, 9200);
        this.addFixedExposedPort(9310, 9300);
        this.addEnv(CLUSTER_NAME, ELASTIC_SEARCH);
        this.withNetworkAliases("testcontainers.elasticsearch.local");
    }
}
