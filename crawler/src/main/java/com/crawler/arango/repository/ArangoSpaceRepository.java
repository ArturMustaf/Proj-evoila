package com.crawler.arango.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.crawler.arango.entity.ArangoSpace;

public interface ArangoSpaceRepository extends ArangoRepository<ArangoSpace, String> {
}
