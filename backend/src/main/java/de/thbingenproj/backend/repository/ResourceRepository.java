package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.Resource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
@RepositoryRestResource
public interface ResourceRepository extends CrudRepository<Resource, String> {
    List<Resource> findAll();

    List<Resource> findAllByVdcID(String vdcID);

    List<Resource> findAllByOrgID(String orgID);

}
