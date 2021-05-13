package de.test.backend.entities.repositories;

import de.test.backend.entities.ressources.ResourceEntity;
import org.springframework.data.repository.CrudRepository;

public interface ResourceEntityRepository extends CrudRepository<ResourceEntity, Long> {
}
