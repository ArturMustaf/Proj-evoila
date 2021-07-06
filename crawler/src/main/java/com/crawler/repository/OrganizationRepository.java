package com.crawler.repository;

import com.crawler.entity.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.ArrayList;

@RepositoryRestResource
public interface OrganizationRepository extends CrudRepository<Organization, String> {
}