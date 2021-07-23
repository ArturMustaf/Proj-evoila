package de.thbingenproj.backend.controller.vcloud;


import de.thbingenproj.backend.entity.Organization;
import de.thbingenproj.backend.model.OrganizationDTO;
import de.thbingenproj.backend.repository.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrganizationController {
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    ModelMapper modelMapper;


    /**
     * stores a new organization
     * @param  organization that should be saved
     * @return the saved organization object or error
     */

    @PostMapping("/create/organizations")
    public Organization postOrganization(@RequestBody OrganizationDTO organization){
        return this.organizationRepository.save(modelMapper.map(organization, Organization.class));
    }

    /**
     * get the requested organization
     * @param  id of the organization
     * @return organization object or null
     */
  
    @GetMapping("/organizations/{id}")
    public Organization getOrganization(@PathVariable String id) {
        return this.organizationRepository.findById(id).orElse(null);
    }
    /**
     * get every stored organization
     * @return all organization objects
     */
    @GetMapping("/admin/organizations")
    public List<Organization> getAllOrganizations() {
        return this.organizationRepository.findAll();
    }
}
