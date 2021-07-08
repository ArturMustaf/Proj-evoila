package de.thbingenproj.backend.repository;


import de.thbingenproj.backend.entity.VApp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface VAppRepository extends CrudRepository<VApp, String> {
    List<VApp> findAll();
    List<VApp> findAllByVdcID(String vdcID);
}
