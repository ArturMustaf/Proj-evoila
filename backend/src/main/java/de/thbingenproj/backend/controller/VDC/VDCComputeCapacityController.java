package de.thbingenproj.backend.controller.VDC;

import de.thbingenproj.backend.entity.VDCComputeCapacity;
import de.thbingenproj.backend.model.VDCComputeCapacityDTO;
import de.thbingenproj.backend.repository.VDCComputeCapacityRepository;
import de.thbingenproj.backend.repository.VDCRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VDCComputeCapacityController {
    @Autowired
    VDCComputeCapacityRepository vdcRepository;

    @Autowired
    ModelMapper modelMapper;

    @PutMapping("create/VDCComputeCapacity")
    public VDCComputeCapacity putVDCComputeCapacity(@RequestBody VDCComputeCapacityDTO vdcComputeCapacity){
        return this.vdcRepository.save(modelMapper.map(vdcComputeCapacity, VDCComputeCapacity.class));
    }
}
