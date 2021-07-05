package de.thbingenproj.backend.controller.VDC;

import de.thbingenproj.backend.entity.VDC;
import de.thbingenproj.backend.entity.VDCComputeCapacity;
import de.thbingenproj.backend.model.VDCDTO;
import de.thbingenproj.backend.repository.VDCRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class VDCController {
    @Autowired
    private VDCRepository VDCRepository;

    @Autowired
    ModelMapper modelMapper;

    @PutMapping("create/VDC")
    public VDC putVDC(@RequestBody VDCDTO VDC){
        return this.VDCRepository.save(modelMapper.map(VDC, VDC.class));
    }

    //ToDo implement deleteVDCComputeCapacity
    @DeleteMapping("delete/VDC/{id}")
    public String deleteVDCComputeCapacity(@PathVariable("id") String id) {
        throw new UnsupportedOperationException("delete/VDCComputeCapacity not implemented yet.");
    }

    @GetMapping("VDC/{id}")
    public VDC getVDCComputeCapacity(@PathVariable String id) {
        return this.VDCRepository.findById(id).orElse(null);
    }
}
