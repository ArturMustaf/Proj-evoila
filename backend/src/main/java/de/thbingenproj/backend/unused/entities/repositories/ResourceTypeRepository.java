package de.thbingenproj.backend.unused.entities.repositories;

import de.thbingenproj.backend.unused.entities.ressources.ResourceType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
/**
 * Basic Spring crud repo
 */
public interface ResourceTypeRepository extends CrudRepository<ResourceType, Long> {
}
