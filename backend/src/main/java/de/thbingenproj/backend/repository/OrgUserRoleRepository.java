package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.OrgUserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.ArrayList;

@RepositoryRestResource
public interface OrgUserRoleRepository extends CrudRepository<OrgUserRole, String> {
    ArrayList<OrgUserRole> findAll();
}
