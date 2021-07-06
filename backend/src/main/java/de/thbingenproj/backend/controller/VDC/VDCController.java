package de.thbingenproj.backend.controller.VDC;

import de.thbingenproj.backend.entity.Organization;
import de.thbingenproj.backend.entity.VDC;
import de.thbingenproj.backend.entity.VDCComputeCapacity;
import de.thbingenproj.backend.model.VDCDTO;
import de.thbingenproj.backend.repository.VDCRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("VDC/{id}")
    public VDC getVDC(@PathVariable String id) {
        return this.VDCRepository.findById(id).orElse(null);
    }

    @GetMapping("VDC")
    public List<VDC> getAllVDCByOrganizationID(String orgID) {
        return this.VDCRepository.findAllByOrgID(orgID);
    }
}
