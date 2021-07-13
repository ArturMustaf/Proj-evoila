package com.crawler.arango.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.crawler.arango.entity.App;

public interface AppRepository extends ArangoRepository<App, String> {
}
