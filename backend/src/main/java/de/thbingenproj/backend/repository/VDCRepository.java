package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.VDC;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.ArrayList;
import java.util.List;

@RepositoryRestResource
public interface VDCRepository extends CrudRepository<VDC, String> {
    List<VDC> findAll();
    List<VDC> findAllByOrgID(String orgID);
}
