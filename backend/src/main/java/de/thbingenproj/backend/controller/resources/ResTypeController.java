package de.thbingenproj.backend.controller.resources;

import de.thbingenproj.backend.model.ResTypeDTO;
import de.thbingenproj.backend.entity.ResType;
import de.thbingenproj.backend.repository.ResTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controller which offers functionality for the frontend to read resource types from the backend
 */
@RestController
public class ResTypeController {
    @Autowired
    ResTypeRepository resTypeRepository;

    @Autowired
    ModelMapper modelMapper;

    /**
     * stores a new resourceType
     * @param  resourceType that should be safed
     * @return the saved ResType object or error
     */
    @PostMapping("/create/resource-types")
    public ResType postResourceType(@RequestBody ResTypeDTO resourceType) {
        return this.resTypeRepository.save(modelMapper.map(resourceType, ResType.class));
    }
    /**
     * @param  id of the resourceType
     * @return ResType object
     */
    @GetMapping("/resource-types/{id}")
    public ResType getResourceType(@PathVariable long id) {
        return this.resTypeRepository.findById(id).orElse(null);
    }

    /**
     * @return all resTypes
     */
    @GetMapping("/admin/resource-types")
    public List<ResType> getAllResourceTypes() {
        return this.resTypeRepository.findAll();
    }




}
