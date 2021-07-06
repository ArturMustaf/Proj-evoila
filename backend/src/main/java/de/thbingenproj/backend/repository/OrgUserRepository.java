package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.OrgNetwork;
import de.thbingenproj.backend.entity.OrgUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.ArrayList;
import java.util.List;

@RepositoryRestResource
public interface OrgUserRepository extends CrudRepository<OrgUser, String> {
    List<OrgUser> findAll();
    List<OrgUser> findAllByOrgID(String orgID);
}
