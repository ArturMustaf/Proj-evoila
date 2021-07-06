package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.VDCComputeCapacity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.ArrayList;

@RepositoryRestResource
public interface VDCComputeCapacityRepository extends CrudRepository<VDCComputeCapacity, Long> {
    ArrayList<VDCComputeCapacity> findAllByVdcID(String vdcID);
}
