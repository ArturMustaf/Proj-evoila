package de.thbingenproj.backend.controller.cf;

import de.thbingenproj.backend.entity.Space;
import de.thbingenproj.backend.model.SpaceDTO;
import de.thbingenproj.backend.repository.SpaceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpaceController {
    @Autowired
    SpaceRepository spaceRepository;
    @Autowired
    ModelMapper modelMapper;
    
    /**
     * Accepts a single space via post request and saves it
     * @param space describes the DTO object of an actual Space
     * @return saved Space for handling correct responses
     */
    @PostMapping("create/spaces")
    public Space postSpace(@RequestBody SpaceDTO space) {
        return this.spaceRepository.save(modelMapper.map(space, Space.class));
    }
    
    /**
     * Returns a single space per ID via get request
     * @param id internal ID of the requested Space
     * @return found Space
     */
    @GetMapping("spaces/{id}")
    public Space getSpace(@PathVariable String id) {
        return this.spaceRepository.findById(id).orElse(null);
    }
    
    /**
     * Returns all spaces for given organization via get request
     * @param id internal ID of the organization
     * @return all found Spaces
     */
    @GetMapping("organizations/{id}/spaces")
    public List<Space> getAllSpacesByOrg(@PathVariable String id) {
        return this.spaceRepository.findAllByOrg(id);
    }
    
    /**
     * Returns all spaces regardless of organization or space via get request
     * @return all found Spaces
     */
    @GetMapping("admin/spaces")
    public List<Space> getAllSpaces() {
        return this.spaceRepository.findAll();
    }
}
