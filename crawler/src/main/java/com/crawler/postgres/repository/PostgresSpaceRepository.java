package com.crawler.postgres.repository;

import com.crawler.postgres.entity.PostgresSpace;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PostgresSpaceRepository extends CrudRepository<PostgresSpace, String> {
}
