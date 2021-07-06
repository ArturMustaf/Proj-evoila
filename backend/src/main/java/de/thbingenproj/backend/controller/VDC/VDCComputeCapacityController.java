package de.thbingenproj.backend.controller.VDC;

import de.thbingenproj.backend.entity.VDC;
import de.thbingenproj.backend.entity.VDCComputeCapacity;
import de.thbingenproj.backend.model.VDCComputeCapacityDTO;
import de.thbingenproj.backend.repository.VDCComputeCapacityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/vdc-compute-capacities/{id}")
    public VDCComputeCapacity getVDCComputeCapacity(@PathVariable long id) {
        return this.VDCComputeCapacityRepository.findById(id).orElse(null);
    }

    @GetMapping("/vdcs/{id}/vdc-compute-capacities")
    public List<VDCComputeCapacity> getAllVDCComputeCapacityByVDCID(String id) {
        return this.VDCComputeCapacityRepository.findAllByVdcID(id);
    }
}
