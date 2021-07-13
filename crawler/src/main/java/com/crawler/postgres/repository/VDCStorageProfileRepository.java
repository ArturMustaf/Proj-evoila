package com.crawler.postgres.repository;

import com.crawler.postgres.entity.VDCStorageProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.ArrayList;

@RepositoryRestResource
public interface VDCStorageProfileRepository extends CrudRepository<VDCStorageProfile, String> {
    ArrayList<VDCStorageProfile> findAll();
}
