package com.crawler.repository;

import com.crawler.entity.VDCComputeCapacity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.ArrayList;

@RepositoryRestResource
public interface VDCComputeCapacityRepository extends CrudRepository<VDCComputeCapacity, Long> {
    ArrayList<VDCComputeCapacity> findAll();
}
