package de.thbingenproj.backend.unused.entities.repositories;


import de.thbingenproj.backend.unused.entities.ressources.Event;
import org.springframework.data.repository.CrudRepository;

/**
 * Basic Spring crud repo
 */
public interface EventRepository extends CrudRepository<Event, Long> {

}

