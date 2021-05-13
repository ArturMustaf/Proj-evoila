package de.test.backend.controller;

import de.test.backend.entities.repositories.ResourceTypeRepository;
import de.test.backend.entities.ressources.ResourceType;
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
    ResourceType getContactInformations(@PathVariable long id) {
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
    ResourceType putContactInformation(@PathVariable long id, @RequestBody ResourceType resourceType) {
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
    ResourceType postCustomer(@RequestBody ResourceType resourceType) {
        return this.resourceTypeRepository.save(resourceType);
    }
}
