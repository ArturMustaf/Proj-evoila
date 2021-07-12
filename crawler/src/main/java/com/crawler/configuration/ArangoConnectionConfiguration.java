package com.crawler.configuration;

import com.arangodb.ArangoDB;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableArangoRepositories
public class ArangoConfiguration {
    @Override
    public ArangoDB.Builder arango() {
        return new ArangoDB.Builder();
    }
}
