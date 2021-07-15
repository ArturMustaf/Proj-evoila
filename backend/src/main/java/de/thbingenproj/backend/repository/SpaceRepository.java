package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.Space;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface SpaceRepository extends CrudRepository<Space, String> {
    List<Space> findAll();
}
