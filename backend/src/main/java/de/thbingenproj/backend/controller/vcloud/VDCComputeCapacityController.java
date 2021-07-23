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
    /**
     * stores a new orgUser
     * @param  vdcComputeCapacity that should be saved
     * @return the saved VDCComputeCapacity object or error
     */
    @PostMapping("/create/vdc-compute-capacities")
    public VDCComputeCapacity postVDCComputeCapacity(@RequestBody VDCComputeCapacityDTO vdcComputeCapacity){
        return this.vdcComputeCapacityRepository.save(modelMapper.map(vdcComputeCapacity, VDCComputeCapacity.class));
    }
    /**
     * get the requested VDCComputeCapacity
     * @param  id of the VDCComputeCapacity
     * @return VDCComputeCapacity object or null
     */
    @GetMapping("/vdc-compute-capacities/{id}")
    public VDCComputeCapacity getVDCComputeCapacities(@PathVariable long id) {
        return this.vdcComputeCapacityRepository.findById(id).orElse(null);
    }

    @GetMapping("/admin/vdc-compute-capacities")
    public List<VDCComputeCapacity> getAllVDCComputeCapacities() {
        return this.vdcComputeCapacityRepository.findAll();
    }
    /**
     * get all VDCComputeCapacities of a vdc
     * @param  id of the vdc
     * @return VDCComputeCapacity object
     */
    @GetMapping("/vdcs/{id}/vdc-compute-capacities")
    public List<VDCComputeCapacity> getAllVDCComputeCapacitiesByVDCID(@PathVariable String id) {
        return this.vdcComputeCapacityRepository.findAllByVdcNumber(id);
    }
    /**
     * get the latest VDCComputeCapacity of a vdc
     * @param  id of the vdc
     * @return VDCComputeCapacity object
     */
    @GetMapping("/vdcs/{id}/vdc-compute-capacities/latest")
    public VDCComputeCapacity getLatestComputeCapacitiesByVDCID(@PathVariable String id) {
        return this.vdcComputeCapacityRepository.findTopByVdcNumberOrderByDateDesc(id);
    }
}
