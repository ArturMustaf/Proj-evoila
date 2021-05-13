package de.test.backend.entities.repositories;

import de.test.backend.entities.prices.Price;
import org.springframework.data.repository.CrudRepository;
/**
 * Basic Spring crud repo
 */
public interface PriceRepository extends CrudRepository<Price, Long> {

}
