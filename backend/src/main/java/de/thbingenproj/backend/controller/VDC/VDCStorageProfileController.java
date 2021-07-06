package de.thbingenproj.backend.controller.VDC;

import de.thbingenproj.backend.repository.VDCStorageProfileRepository;
import de.thbingenproj.backend.entity.VDCStorageProfile;
import de.thbingenproj.backend.model.VDCStorageProfileDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VDCStorageProfileController {
    @Autowired
    VDCStorageProfileRepository VDCStorageProfileRepository;

    @Autowired
    ModelMapper modelMapper;

    @PutMapping("create/VDCStorageProfile")
    public VDCStorageProfile putVDCStorageProfile(@RequestBody VDCStorageProfileDTO vdcStorageProfile){
        return this.VDCStorageProfileRepository.save(modelMapper.map(vdcStorageProfile, VDCStorageProfile.class));
    }

    @GetMapping("VDCStorageProfile/{id}")
    public VDCStorageProfile getVDCStorageProfile(@PathVariable String id) {
        return this.VDCStorageProfileRepository.findById(id).orElse(null);
    }

    @GetMapping("VDCStorageProfile")
    public List<VDCStorageProfile> getAllVDCStorageProfileByVdcID(String vdcID) {
        return this.VDCStorageProfileRepository.findAllByVdcID(vdcID);
    }
}
