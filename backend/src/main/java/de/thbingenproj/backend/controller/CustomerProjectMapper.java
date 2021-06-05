package de.thbingenproj.backend.controller;

import de.thbingenproj.backend.entities.repositories.CustomerRepository;
import de.thbingenproj.backend.entities.repositories.ProjectRepository;
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
    public void update(@PathVariable long userid, @PathVariable long projectid) throws NullPointerException {
        var customer = customerRepository.findById(userid).orElse(null);
        var project = projectRepository.findById(projectid).orElse(null);
        customer.getProjects().add(project);
        customerRepository.save(customer);
    }

}
