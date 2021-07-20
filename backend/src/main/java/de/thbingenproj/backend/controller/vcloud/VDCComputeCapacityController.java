package de.thbingenproj.backend.controller.vcloud;

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
    VDCComputeCapacityRepository vdcComputeCapacityRepository;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/create/vdc-compute-capacities")
    public VDCComputeCapacity postVDCComputeCapacity(@RequestBody VDCComputeCapacityDTO vdcComputeCapacity){
        return this.vdcComputeCapacityRepository.save(modelMapper.map(vdcComputeCapacity, VDCComputeCapacity.class));
    }

    @GetMapping("/vdc-compute-capacities/{id}")
    public VDCComputeCapacity getVDCComputeCapacities(@PathVariable long id) {
        return this.vdcComputeCapacityRepository.findById(id).orElse(null);
    }

    @GetMapping("/admin/vdc-compute-capacities")
    public List<VDCComputeCapacity> getAllVDCComputeCapacities() {
        return this.vdcComputeCapacityRepository.findAll();
    }

    @GetMapping("/vdcs/{id}/vdc-compute-capacities")
    public List<VDCComputeCapacity> getAllVDCComputeCapacitiesByVDCID(@PathVariable String id) {
        return this.vdcComputeCapacityRepository.findAllByVdcID(id);
    }

    @GetMapping("/vdcs/{id}/vdc-compute-capacities/latest")
    public VDCComputeCapacity getLatestComputeCapacitiesByVDCID(@PathVariable String id) {
        return this.vdcComputeCapacityRepository.findByVdcIDTopByOrderByDate(id);
    }
}
