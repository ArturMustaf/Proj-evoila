package de.test.backend.controller;

import de.test.backend.entities.repositories.CustomerRepository;
import de.test.backend.entities.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
/**
 * Basic Controller which offers functionality for the frontend to read,put and post customer data from the frontend
 *
 */
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    /**
     * Put Mapping for object
     *
     * @param id       of customer
     * @param customer put parameter
     * @return saved object
     */
    @PutMapping("create/customers/{id}")
    public Customer createCustomer(@PathVariable long id, @RequestBody Customer customer) {
        customer.setId(id);
        return this.customerRepository.save(customer);
    }

    /**
     * Post Mapping to save new customers
     *
     * @param customer
     * @return saved object
     */
    @PostMapping("create/customers")
    public Customer postCustomer(@RequestBody Customer customer) {
        return this.customerRepository.save(customer);
    }

    /**
     * @param not implemented
     * @return
     */
    @GetMapping("create/customers/linked/{id}")
    public Customer isLinked(@PathVariable long id) {
        return customerRepository.findById(id).orElse(null);
    }

    /**
     * Read Customer data
     *
     * @param id read by id
     * @return
     */
    @GetMapping("create/customers/{id}")
    public Customer getCustomer(@PathVariable long id) {
        return this.customerRepository.findById(id).orElse(null);
    }
}
