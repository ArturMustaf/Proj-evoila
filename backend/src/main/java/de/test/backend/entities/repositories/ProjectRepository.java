package de.test.backend.entities.repositories;

import de.test.backend.entities.prices.Project;
import de.test.backend.entities.ressources.ResourceLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource()
/**
 * Basic Spring crud repo
 */
public interface ProjectRepository extends CrudRepository<Project, Long> {
    /**
     * @param id of the project for which the logs should be searched
     * @return
     */
    @Query("SELECT p.logs FROM Project p WHERE p.id = :id")
    List<ResourceLog> findAllLogs(@Param("id") Long id);


}
