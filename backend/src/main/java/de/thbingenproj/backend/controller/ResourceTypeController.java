package de.thbingenproj.backend.controller;

import de.thbingenproj.backend.entities.repositories.ResourceTypeRepository;
import de.thbingenproj.backend.entities.ressources.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ResourceTypeController {

    @Autowired
    ResourceTypeRepository resourceTypeRepository;

    /**
     * Read ContactInformation by id
     *
     * @param id
     * @return
     */
    @GetMapping("create/resourceType/{id}")
    public ResourceType getContactInformations(@PathVariable long id) {
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
    public ResourceType putContactInformation(@PathVariable long id, @RequestBody ResourceType resourceType) {
        resourceType.setId(id);
        return this.resourceTypeRepository.save(resourceType);
    }

    /**
     * Create new ResourceType
     *
     * @param resourceType
     * @return
     */
    @PostMapping("create/resourceType")
    public ResourceType postCustomer(@RequestBody ResourceType resourceType) {
        return this.resourceTypeRepository.save(resourceType);
    }
}
