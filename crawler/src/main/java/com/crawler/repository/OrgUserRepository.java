package com.crawler.repository;

import com.crawler.entity.OrgUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.ArrayList;

@RepositoryRestResource
public interface OrgUserRepository extends CrudRepository<OrgUser, String> {
    ArrayList<OrgUser> findAll();
}
