package com.crawler.postgres.repository;

import com.crawler.postgres.entity.OSB;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface OSBRepository extends CrudRepository<OSB, String> {
    List<OSB> findAll();
}
