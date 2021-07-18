package de.thbingenproj.backend.controller.resources;

import de.thbingenproj.backend.entity.Resource;
import de.thbingenproj.backend.model.ResourceDTO;
import de.thbingenproj.backend.repository.ResourceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller which offers functionality for the frontend to read resource  from the backend
 */
@RestController
public class ResourceController {
    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    ModelMapper modelMapper;

    /**
     * stores a new resource
     * @param  resource that should be safed
     * @return the saved resource object or error
     */
    @PostMapping("/create/resources")
    public Resource postResource(@RequestBody ResourceDTO resource) {
        return this.resourceRepository.save(modelMapper.map(resource, Resource.class));
    }
    /**
     * @param  id of the resource
     * @return resource object
     */
    @GetMapping("/resources/{id}")
    public Resource getResource(@PathVariable String id) {
        return this.resourceRepository.findById(id).orElse(null);
    }
    /**
     * @return all resources
     */
    @GetMapping("/admin/resources")
    public List<Resource> getAllResources() {
        return this.resourceRepository.findAll();
    }


    /**
     * returns all resources used or in use of an organization
     * @param  orgID of the organization
     * @return resource objects
     */
    @GetMapping("/resources/organizations/{orgID}")
    public List<Resource> getALlResourcesByOrgID(@PathVariable String orgID){
        return this.resourceRepository.findAllByOrgID(orgID);
    }

    /**
     * returns all resources used or in use of a vdc
     * @param  vdcID of the organization
     * @return resource objects of vdc
     */
    @GetMapping("/resources/vdcs/{vdcID}")
    public List<Resource> getALlResourcesByVdcID(@PathVariable String vdcID) {
        return this.resourceRepository.findAllByVdcID(vdcID);
    }

    /**
     * returns all resources used or in use of an organization in a specific time interval
     * @param  id of the organization
     * @param from start date
     * @param until end date
     * @return resource objects of vdc
     */
    @GetMapping("/organizations/{id}/resources/{from}/{until}")
    public List<Resource>getResourcesByOrgIdInGivenTime(@PathVariable String id, @PathVariable long from, @PathVariable long until){
        var allResources=getALlResourcesByOrgID(id);
        allResources.stream()
                .filter(resource -> (resource.getStartDate()<until&&  resource.getEndDate()> from )
                        || (resource.getStartDate()>from && resource.getStartDate()<until)
                        ||(resource.getStartDate()<until && resource.getEndDate()==0))
                .collect(Collectors.toList());

        return allResources;

    }
    /**
     * returns all resources used or in use of a vdc in a specific time interval
     * @param vdcID of the organization
     * @param from start date
     * @param until end date
     * @return resource objects of vdc
     */

    @GetMapping("/resources/vdcs/{vdcID}/{from}/{until}")
    public List<Resource>getResourcesByVdcIdInGivenTime(@PathVariable String vdcID, @PathVariable long from, @PathVariable long until){
        var allResources=getALlResourcesByOrgID(vdcID);
        allResources.stream()
                .filter(resource -> (resource.getStartDate()<until&&  resource.getEndDate()> from )
                        || (resource.getStartDate()>from && resource.getStartDate()<until)
                        ||(resource.getStartDate()<until && resource.getEndDate()==0))
                .collect(Collectors.toList());

        return allResources;

    }
}
