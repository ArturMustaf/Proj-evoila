package de.thbingenproj.backend.controller.Organization;


import de.thbingenproj.backend.entity.Organization;
import de.thbingenproj.backend.model.OrganizationDTO;
import de.thbingenproj.backend.repository.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrganizationController {
    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    ModelMapper modelMapper;

    //ToDo implement deleteOrganization
    @DeleteMapping("delete/organization/{id}")
    public String deleteOrganization(@PathVariable("id") String id) {
        throw new UnsupportedOperationException("delete/organization not implemented yet.");
    }

    @GetMapping("organization/{id}")
    public Organization getOrganization(@PathVariable String id) {
        return this.organizationRepository.findById(id).orElse(null);
    }

    @PutMapping("create/organization")
    public Organization putOrganization(@RequestBody OrganizationDTO organization){
        return this.organizationRepository.save(modelMapper.map(organization, Organization.class));
    }
}
