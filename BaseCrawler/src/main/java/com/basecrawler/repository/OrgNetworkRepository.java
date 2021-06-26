package com.basecrawler.repository;

import com.basecrawler.entity.OrgNetwork;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OrgNetworkRepository extends CrudRepository<OrgNetwork, String> {
}
