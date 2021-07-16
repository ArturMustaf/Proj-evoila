package com.crawler.postgres.repository;

import com.crawler.postgres.entity.OrgNetwork;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface OrgNetworkRepository extends CrudRepository<OrgNetwork, String> {
    List<OrgNetwork> findAll();
}
