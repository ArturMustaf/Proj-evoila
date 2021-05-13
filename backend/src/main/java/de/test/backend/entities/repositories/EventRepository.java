package de.test.backend.entities.repositories;


import de.test.backend.entities.ressources.Event;
import org.springframework.data.repository.CrudRepository;

/**
 * Basic Spring crud repo
 */
public interface EventRepository extends CrudRepository<Event, Long> {

}

