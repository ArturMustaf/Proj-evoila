package de.thbingenproj.backend.unused.entities.repositories;

import de.thbingenproj.backend.unused.entities.user.Customer;
import de.thbingenproj.backend.unused.entities.user.agent.Agent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/**
 * Basic Spring crud repo
 */
public interface AgentRepository extends CrudRepository<Agent, Long> {
    List<Customer> findAllByContactInformationId(long id);
}
