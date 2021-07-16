package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.ResType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResTypeRepository extends CrudRepository<ResType,String> {
    List<ResType>findAll();
    List<ResType> findAllByTypeOfResource(String typeOfResource);
}
