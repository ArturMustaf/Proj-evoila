package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.Prices;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PricesRepository extends CrudRepository<Prices, String> {
    List<Prices> findAll();
    List<Prices> findAllByResourceID(String resourceID);
    Prices findByResourceIDAndEndIsNull(String resourceID);


}
