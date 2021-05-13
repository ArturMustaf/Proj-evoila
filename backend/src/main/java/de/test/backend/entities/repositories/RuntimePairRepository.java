package de.test.backend.entities.repositories;

import de.test.backend.entities.prices.Project;
import de.test.backend.entities.prices.RuntimePair;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface RuntimePairRepository extends CrudRepository<RuntimePair, Long> {
    @Query("SELECT p FROM RuntimePair p WHERE p.entity.id = :id and p.stopped is null")
    List<RuntimePair> findRuntimePairByEntityIdAndStopped(long id);

    @Query("Select p from RuntimePair p WHERE p.started.created between :started and :ended and p.project = :project")
    List<RuntimePair> findRuntimePairsByEndDateAndStartDateAndProject(@Param("started") Date started, @Param("ended") Date ended, Project project);

    @Query("Select p from RuntimePair  p WHERE p.project = :project and p.stopped is null")
    List<RuntimePair> findRuntimePairsWhichAreActive(Project project);
}

