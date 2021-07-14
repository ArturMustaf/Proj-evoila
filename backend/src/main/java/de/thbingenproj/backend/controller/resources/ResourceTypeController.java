package de.thbingenproj.backend.controller.resources;

import de.thbingenproj.backend.entities.ressources.ResourceTypeDTO;
import de.thbingenproj.backend.entity.ResourceType;
import de.thbingenproj.backend.repository.ResourceTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResourceTypeController {
    @Autowired
    ResourceTypeRepository resourceTypeRepository;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/admin/create/resourceTypes")
    public ResourceType postResourceType(@RequestBody ResourceTypeDTO resourceType) {
        return this.resourceTypeRepository.save(modelMapper.map(resourceType, ResourceType.class));
    }

    @GetMapping("/resourcetypes/{id}")
    public ResourceType getResourceType(@PathVariable String id) {
        return this.resourceTypeRepository.findById(id).orElse(null);
    }

    @GetMapping("/admin/resourceTypes")
    public List<ResourceType> getAllResourceTypes() {
        return this.resourceTypeRepository.findAll();
    }

    @GetMapping("/admin/resourceTypes/{typeOfResource}")
    public List<ResourceType> getAllByResourceTypes(@PathVariable String typeOfResource) {
        return this.resourceTypeRepository.findAllByTypeOfResource(typeOfResource);
    }


}
