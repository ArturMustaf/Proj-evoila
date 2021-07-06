package de.thbingenproj.backend.controller.Organization;


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

    @PostMapping("/create/organizations")
    public Organization postOrganization(@RequestBody OrganizationDTO organization){
        return this.organizationRepository.save(modelMapper.map(organization, Organization.class));
    }

    @GetMapping("/organizations/{id}")
    public Organization getOrganization(@PathVariable String id) {
        return this.organizationRepository.findById(id).orElse(null);
    }

    @GetMapping("/admin/organizations")
    public List<Organization> getAllOrganization() {
        return this.organizationRepository.findAll();
    }
}
