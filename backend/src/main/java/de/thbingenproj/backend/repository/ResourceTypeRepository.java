package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.ResourceType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResourceTypeRepository extends CrudRepository<ResourceType,String> {
    List<ResourceType>findAll();
    List<ResourceType> findAllByTypeOfResource(String typeOfResource);
}
