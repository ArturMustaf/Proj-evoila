package de.test.backend.controller;

import de.test.backend.entities.prices.Project;
import de.test.backend.entities.repositories.CustomerRepository;
import de.test.backend.entities.repositories.ProjectRepository;
import de.test.backend.entities.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is currently public but should be later than made private, since it should be authentificated that only a admin can perform these actions
 * Was disabled for presentation purposes
 */
@RestController
public class CustomerProjectMapper {
    @Autowired
    public CustomerRepository customerRepository;
    @Autowired
    public ProjectRepository projectRepository;

    @GetMapping("/public/createUserLink/{userid}/{projectid}")
    void update(@PathVariable long userid, @PathVariable long projectid) {
        Customer customer = customerRepository.findById(userid).orElse(null);
        Project project = projectRepository.findById(projectid).orElse(null);
        customer.getProjects().add(project);
        customerRepository.save(customer);
    }

}
