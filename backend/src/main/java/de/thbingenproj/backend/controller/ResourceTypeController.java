package de.thbingenproj.backend.controller;

import de.thbingenproj.backend.entities.repositories.ResourceTypeRepository;
import de.thbingenproj.backend.entities.ressources.ResourceType;
import de.thbingenproj.backend.entities.ressources.ResourceTypeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ResourceTypeController {

    @Autowired
    ResourceTypeRepository resourceTypeRepository;
    @Autowired
    ModelMapper modelMapper;
    /**
     * Read ContactInformation by id
     *
     * @param id
     * @return
     */
    @GetMapping("create/resourceType/{id}")
    public ResourceType getResourceType(@PathVariable long id) {
        return this.resourceTypeRepository.findById(id).orElse(null);
    }

    /**
     * put ResourceType
     *
     * @param id
     * @param resourceType
     * @return
     */
    @PutMapping("create/resourceType/{id}")
    public ResourceType putResourceType(@PathVariable long id, @RequestBody ResourceTypeDTO resourceType) {
        resourceType.setId(id);
        return this.resourceTypeRepository.save(modelMapper.map(resourceType, ResourceType.class));
    }

    /**
     * Create new ResourceType
     *
     * @param resourceType
     * @return
     */
    @PostMapping("create/resourceType")
    public ResourceType postResourceType(@RequestBody ResourceTypeDTO resourceType) {
        return this.resourceTypeRepository.save(modelMapper.map(resourceType, ResourceType.class));
    }
}
