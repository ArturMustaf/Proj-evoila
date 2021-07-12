package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.VDCStorageProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.ArrayList;


@RepositoryRestResource
public interface VDCStorageProfileRepository extends CrudRepository<VDCStorageProfile, String> {
    ArrayList<VDCStorageProfile> findAllByVdcID(String vdcID);
}
