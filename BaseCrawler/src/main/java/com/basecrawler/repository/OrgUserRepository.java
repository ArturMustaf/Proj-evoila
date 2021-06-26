package com.basecrawler.repository;

import com.basecrawler.entity.OrgUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OrgUserRepository extends CrudRepository<OrgUser, String> {
}
