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

    @PostMapping("create/spaces")
    public Space postSpace(@RequestBody SpaceDTO space) {
        return this.spaceRepository.save(modelMapper.map(space, Space.class));
    }

    @GetMapping("spaces/{id}")
    public Space getSpace(@PathVariable String id) {
        return this.spaceRepository.findById(id).orElse(null);
    }

    @GetMapping("organizations/{id}/spaces")
    public List<Space> getAllSpacesByOrg(@PathVariable String id) {
        return this.spaceRepository.findAllByOrg(id);
    }

    @GetMapping("admin/spaces")
    public List<Space> getAllSpaces() {
        return this.spaceRepository.findAll();
    }
}
