package com.crawler.configuration;

import com.arangodb.ArangoDB;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.ArangoConfiguration;
import org.springframework.context.annotation.Configuration;


/**
 * @see "https://www.arangodb.com/docs/stable/drivers/spring-data-getting-started.html"
 * create a file named resources/arangodb.properties
 * and set:
 * arangodb.hosts=
 * arangodb.user=
 * arangodb.password=
 */
@Configuration
@EnableArangoRepositories
public class ArangoConnectionConfiguration implements ArangoConfiguration {
    @Override
    public ArangoDB.Builder arango() {
                return new ArangoDB.Builder();
    }
    @Override
    public String database() {
        return "prom";
    }
}
