package de.thbingenproj.backend.repository;


import de.thbingenproj.backend.entity.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.ArrayList;

@RepositoryRestResource
public interface OrganizationRepository extends CrudRepository<Organization, String> {
    ArrayList<Organization> findAll();
}