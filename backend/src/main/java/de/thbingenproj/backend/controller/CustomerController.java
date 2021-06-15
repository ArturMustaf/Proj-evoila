package de.thbingenproj.backend.controller;

import de.thbingenproj.backend.entities.repositories.CustomerRepository;
import de.thbingenproj.backend.entities.user.Customer;
import de.thbingenproj.backend.entities.user.CustomerDTO;
import org.modelmapper.ModelMapper;
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
    @Autowired
    ModelMapper modelMapper;

    /**
     * Put Mapping for object
     *
     * @param id       of customer
     * @param customer put parameter
     * @return saved object
     */
    @PutMapping("create/customers/{id}")
    public Customer putCustomer(@PathVariable long id, @RequestBody CustomerDTO customer) {
        customer.setId(id);
        return this.customerRepository.save(modelMapper.map(customer, Customer.class));
    }

    /**
     * Post Mapping to save new customers
     *
     * @param customer
     * @return saved object
     */
    @PostMapping("create/customers")
    public Customer postCustomer(@RequestBody CustomerDTO customer) {
        return this.customerRepository.save(modelMapper.map(customer, Customer.class));
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
