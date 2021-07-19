package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.Prices;
import de.thbingenproj.backend.entity.Resource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
@RepositoryRestResource
public interface PricesRepository extends CrudRepository<Prices, String> {
    List<Prices> findAll();
    List<Prices> findAllByResourceID(String resourceID);
    Prices findByResourceIDAndEndDateIsNull(String resourceID);

    List<Prices> findAllByVdcID(String vdcID);

    List<Prices> findAllByOrgID(String orgID);

}
