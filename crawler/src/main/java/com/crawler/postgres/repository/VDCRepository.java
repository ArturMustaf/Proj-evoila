package com.crawler.postgres.repository;

import com.crawler.postgres.entity.VDC;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface VDCRepository extends CrudRepository<VDC, String> {
    List<VDC> findAll();
}
