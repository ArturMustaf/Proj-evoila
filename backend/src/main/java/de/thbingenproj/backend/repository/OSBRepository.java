package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.OSB;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface OSBRepository extends CrudRepository<OSB, String> {
    OSB findTopByOrderByDtDesc();
    List<OSB> findAll();
    List<OSB> findAllByOrg(String org);
    List<OSB> findAllBySpace(String space);
}
