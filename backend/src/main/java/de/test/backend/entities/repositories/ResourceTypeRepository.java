package de.test.backend.entities.repositories;

import de.test.backend.entities.ressources.ResourceType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
/**
 * Basic Spring crud repo
 */
public interface ResourceTypeRepository extends CrudRepository<ResourceType, Long> {
}
