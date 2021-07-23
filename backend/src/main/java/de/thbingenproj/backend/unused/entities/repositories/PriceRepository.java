package de.thbingenproj.backend.unused.entities.repositories;

import de.thbingenproj.backend.unused.entities.prices.Price;
import org.springframework.data.repository.CrudRepository;
/**
 * Basic Spring crud repo
 */
public interface PriceRepository extends CrudRepository<Price, Long> {

}
