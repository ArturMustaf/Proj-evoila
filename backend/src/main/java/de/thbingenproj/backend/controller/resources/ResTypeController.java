package de.thbingenproj.backend.controller.resources;

import de.thbingenproj.backend.model.ResTypeDTO;
import de.thbingenproj.backend.entity.ResType;
import de.thbingenproj.backend.repository.ResTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResTypeController {
    @Autowired
    ResTypeRepository resTypeRepository;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/create/resource-types")
    public ResType postResourceType(@RequestBody ResTypeDTO resourceType) {
        return this.resTypeRepository.save(modelMapper.map(resourceType, ResType.class));
    }

    @GetMapping("/resource-types/{id}")
    public ResType getResourceType(@PathVariable String id) {
        return this.resTypeRepository.findById(id).orElse(null);
    }

    @GetMapping("/admin/resource-types")
    public List<ResType> getAllResourceTypes() {
        return this.resTypeRepository.findAll();
    }




}
