package de.thbingenproj.backend.controller.resources;

import de.thbingenproj.backend.entity.Resource;
import de.thbingenproj.backend.model.ResourceDTO;
import de.thbingenproj.backend.repository.ResourceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResourceController {
    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/create/resources")
    public Resource postResource(@RequestBody ResourceDTO resource) {
        return this.resourceRepository.save(modelMapper.map(resource, Resource.class));
    }

    @GetMapping("/resources/{id}")
    public Resource getResource(@PathVariable String id) {
        return this.resourceRepository.findById(id).orElse(null);
    }

    @GetMapping("/admin/resources")
    public List<Resource> getAllResources() {
        return this.resourceRepository.findAll();
    }

    //findyOrgID
    @GetMapping("/resources/organizations/{orgID}")
    public List<Resource> getALlResourcesByOrgID(@PathVariable String orgID){
        return this.resourceRepository.findAllByOrgID(orgID);
    }
    //findByVDCID
    @GetMapping("/resources/vdcs/{vdcID}")
    public List<Resource> getALlResourcesByVdcID(@PathVariable String vdcID) {
        return this.resourceRepository.findAllByVdcID(vdcID);
    }
}
