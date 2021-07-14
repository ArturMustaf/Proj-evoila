package com.crawler.postgres.repository;

import com.crawler.postgres.entity.App;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AppRepository extends CrudRepository<App, String> {
    List<App> findAll();
}
