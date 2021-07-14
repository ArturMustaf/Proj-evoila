package de.thbingenproj.backend.repository;

import de.thbingenproj.backend.entity.Price;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PriceRepository extends CrudRepository<Price, String> {
    List<Price> findAll();
    List<Price> findAllByResourceID(String resourceID);
    Price findByResourceIDAndEndIsNull(String resourceID);


}
