package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.OSB;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OSBRepository extends CrudRepository<OSB, String> {
    OSB findTopByOrderByDtDesc();
}
