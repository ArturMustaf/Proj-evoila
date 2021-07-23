package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.App;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AppRepository extends CrudRepository<App, String> {
    App findTopByOrderByDtDesc();
    List<App> findAll();
    List<App> findAllByOrg(String org);
    List<App> findAllBySpace(String space);
    @Query("select distinct a.space from App a where a.org = ?1")
    List<String> findAllDistinctSpacesByOrg(String org);
    @Query("select distinct a.guid from App a where a.space = ?1 and a.org = ?2")
    List<String> findAllGUIDBySpaceAndOrg(String space, String org);
    App findTopByGuidOrderByDtDesc(String guid);
}
