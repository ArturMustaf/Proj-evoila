package de.thbingenproj.backend.entities.repositories;

import de.thbingenproj.backend.entities.user.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource()
/**
 * Basic Spring crud repo
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findAllByContactInformationId(long id);
    //List<Customer> findAll();

    Customer findCustomerByUserId(String userId);


}
