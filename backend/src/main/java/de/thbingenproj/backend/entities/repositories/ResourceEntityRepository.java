package de.thbingenproj.backend.entities.repositories;

import de.thbingenproj.backend.entities.ressources.ResourceEntity;
import org.springframework.data.repository.CrudRepository;

public interface ResourceEntityRepository extends CrudRepository<ResourceEntity, Long> {
}
