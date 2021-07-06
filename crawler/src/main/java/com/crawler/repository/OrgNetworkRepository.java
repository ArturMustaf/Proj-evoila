package com.crawler.repository;

import com.crawler.entity.OrgNetwork;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.ArrayList;

@RepositoryRestResource
public interface OrgNetworkRepository extends CrudRepository<OrgNetwork, String> {
}
