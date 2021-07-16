package com.crawler.postgres.repository;

import com.crawler.postgres.entity.OrgUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface OrgUserRepository extends CrudRepository<OrgUser, String> {
    List<OrgUser> findAll();
}
