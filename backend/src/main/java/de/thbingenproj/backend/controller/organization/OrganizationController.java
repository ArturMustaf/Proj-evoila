package de.thbingenproj.backend.controller.organization;


import de.thbingenproj.backend.entity.Organization;
import de.thbingenproj.backend.model.OrganizationDTO;
import de.thbingenproj.backend.repository.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrganizationController {

    private final OrganizationRepository organizationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrganizationController(OrganizationRepository organizationRepository, ModelMapper modelMapper) {
        this.organizationRepository = organizationRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create/organizations")
    public Organization postOrganization(@RequestBody OrganizationDTO organization){
        return this.organizationRepository.save(modelMapper.map(organization, Organization.class));
    }

    @GetMapping("/organizations/{id}")
    public Organization getOrganization(@PathVariable String id) {
        return this.organizationRepository.findById(id).orElse(null);
    }

    @GetMapping("/admin/organizations")
    public List<Organization> getAllOrganizations() {
        return this.organizationRepository.findAll();
    }
}
