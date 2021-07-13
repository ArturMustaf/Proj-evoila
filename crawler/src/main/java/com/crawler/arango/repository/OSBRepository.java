package com.crawler.arango.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.crawler.arango.entity.OSB;

public interface OSBRepository extends ArangoRepository<OSB, String> {
}
