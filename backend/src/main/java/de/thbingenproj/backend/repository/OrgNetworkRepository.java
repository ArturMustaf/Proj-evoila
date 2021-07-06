package de.thbingenproj.backend.repository;


import de.thbingenproj.backend.entity.OrgNetwork;
import de.thbingenproj.backend.entity.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.ArrayList;
import java.util.List;

@RepositoryRestResource
public interface OrgNetworkRepository extends CrudRepository<OrgNetwork, String> {
    List<OrgNetwork> findAll();
    List<OrgNetwork> findAllByVdcID(String vdcID);
}
