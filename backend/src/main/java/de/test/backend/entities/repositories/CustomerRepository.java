package de.test.backend.entities.repositories;

import de.test.backend.entities.user.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource()
/**
 * Basic Spring crud repo
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findAllByContactInformation_Id(long id);

    Customer findCustomerByUserId(String userId);


}
