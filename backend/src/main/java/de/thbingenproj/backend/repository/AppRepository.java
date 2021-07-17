package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.App;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AppRepository extends CrudRepository<App, String> {
    App findTopByOrderByDtDesc();
    List<App> findAll();
    List<App> findAllByOrg(String org);
    List<App> findAllBySpace(String space);
}
