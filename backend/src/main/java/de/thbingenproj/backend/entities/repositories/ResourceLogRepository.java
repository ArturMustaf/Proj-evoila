package de.thbingenproj.backend.entities.repositories;

import de.thbingenproj.backend.entities.ressources.ResourceLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource()
/**
 * Basic Spring crud repo
 */
public interface ResourceLogRepository extends CrudRepository<ResourceLog, Long> {

}
