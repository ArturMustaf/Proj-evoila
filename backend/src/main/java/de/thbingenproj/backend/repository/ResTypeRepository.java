package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.ResType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
@RepositoryRestResource
public interface ResTypeRepository extends CrudRepository<ResType,String> {
    List<ResType>findAll();
    List<ResType> findAllByTypeOfResource(String typeOfResource);
}
