package de.thbingenproj.backend.controller.vcloud;

import de.thbingenproj.backend.entity.VDC;
import de.thbingenproj.backend.model.VDCDTO;
import de.thbingenproj.backend.repository.VDCRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VDCController {
    @Autowired
    private VDCRepository vdcRepository;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/create/vdcs")
    public VDC postVDC(@RequestBody VDCDTO vdc){
        return this.vdcRepository.save(modelMapper.map(vdc, VDC.class));
    }

    @GetMapping("/vdcs/{id}")
    public VDC getVDCs(@PathVariable String id) {
        return this.vdcRepository.findById(id).orElse(null);
    }

    @GetMapping("/organizations/{orgID}/vdcs")
    public List<VDC> getAllVDCsByOrganizationID(@PathVariable String orgID) {
        return this.vdcRepository.findAllByOrgID(orgID);
    }
}
