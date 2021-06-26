package com.basecrawler.repository;

import com.basecrawler.entity.OrgUserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OrgUserRoleRepository extends CrudRepository<OrgUserRole, String> {
}
