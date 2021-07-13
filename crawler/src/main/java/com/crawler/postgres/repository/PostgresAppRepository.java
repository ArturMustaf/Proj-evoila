package com.crawler.postgres.repository;

import com.crawler.postgres.entity.PostgresApp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PostgresAppRepository extends CrudRepository<PostgresApp, String> {
}
