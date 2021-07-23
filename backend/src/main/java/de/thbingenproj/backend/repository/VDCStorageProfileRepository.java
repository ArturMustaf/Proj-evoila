package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.VDCStorageProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;


@RepositoryRestResource
public interface VDCStorageProfileRepository extends CrudRepository<VDCStorageProfile, String> {
    List<VDCStorageProfile> findAllByVdcID(String vdcID);
    List<VDCStorageProfile> findAll();

    /**
     * Finds latest Storage profile
     * Question if there can be multiple profiles active at once
     * @param vdcID
     * @return latest storage profile
     */
    VDCStorageProfile findTopByVdcIDOrderByDateDesc(String vdcID);
}
