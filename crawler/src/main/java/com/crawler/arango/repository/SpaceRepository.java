package com.crawler.arango.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.crawler.arango.entity.Space;

public interface SpaceRepository extends ArangoRepository<Space, String> {
}
