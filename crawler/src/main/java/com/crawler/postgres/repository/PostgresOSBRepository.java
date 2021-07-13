package com.crawler.postgres.repository;

import com.crawler.postgres.entity.PostgresOSB;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PostgresOSBRepository extends CrudRepository<PostgresOSB, String> {
}
