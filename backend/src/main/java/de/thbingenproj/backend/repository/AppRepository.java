package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.App;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AppRepository extends CrudRepository<App, String> {
    App findTopByOrderByDtDesc();
}
