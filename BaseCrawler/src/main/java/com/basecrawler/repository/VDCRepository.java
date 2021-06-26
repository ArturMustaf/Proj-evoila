package com.basecrawler.repository;

import com.basecrawler.entity.VDC;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface VDCRepository extends CrudRepository<VDC, String> {
}
