package de.thbingenproj.backend.entities.repositories;

import de.thbingenproj.backend.entities.prices.Price;
import org.springframework.data.repository.CrudRepository;
/**
 * Basic Spring crud repo
 */
public interface PriceRepository extends CrudRepository<Price, Long> {

}
