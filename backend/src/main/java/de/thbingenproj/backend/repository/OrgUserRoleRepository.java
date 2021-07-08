package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.OrgUserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface OrgUserRoleRepository extends CrudRepository<OrgUserRole, String> {
    List<OrgUserRole> findAll();
    List<OrgUserRole> findAllByOrgID(String orgID);
}
