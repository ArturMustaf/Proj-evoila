package de.thbingenproj.backend.controller.VDC;

import de.thbingenproj.backend.entity.VDCComputeCapacity;
import de.thbingenproj.backend.model.VDCComputeCapacityDTO;
import de.thbingenproj.backend.repository.VDCComputeCapacityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class VDCComputeCapacityController {
    @Autowired
    VDCComputeCapacityRepository VDCComputeCapacityRepository;

    @Autowired
    ModelMapper modelMapper;

    @PutMapping("create/VDCComputeCapacity")
    public VDCComputeCapacity putVDCComputeCapacity(@RequestBody VDCComputeCapacityDTO VDCComputeCapacity){
        return this.VDCComputeCapacityRepository.save(modelMapper.map(VDCComputeCapacity, VDCComputeCapacity.class));
    }

    @GetMapping("VDCComputeCapacity/{id}")
    public VDCComputeCapacity getVDCComputeCapacity(@PathVariable long id) {
        return this.VDCComputeCapacityRepository.findById(id).orElse(null);
    }
}
