package de.thbingenproj.backend.controller.Organization;


import de.thbingenproj.backend.entity.Organization;
import de.thbingenproj.backend.model.OrganizationDTO;
import de.thbingenproj.backend.repository.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganizationController {
    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    ModelMapper modelMapper;

    @PutMapping("create/organization")
    public Organization putOrganization(@RequestBody OrganizationDTO organization){
        return this.organizationRepository.save(modelMapper.map(organization, Organization.class));
    }
}
