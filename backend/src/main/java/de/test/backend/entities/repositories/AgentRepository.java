package de.test.backend.entities.repositories;

import de.test.backend.entities.user.Customer;
import de.test.backend.entities.user.agent.Agent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/**
 * Basic Spring crud repo
 */
public interface AgentRepository extends CrudRepository<Agent, Long> {
    List<Customer> findAllByContactInformation_Id(long id);
}
