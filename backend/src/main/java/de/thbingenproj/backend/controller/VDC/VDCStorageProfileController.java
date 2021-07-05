package de.thbingenproj.backend.controller.VDC;

import de.thbingenproj.backend.repository.VDCStorageProfileRepository;
import de.thbingenproj.backend.entity.VDCStorageProfile;
import de.thbingenproj.backend.model.VDCStorageProfileDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public VDCStorageProfile getOrgNetwork(@PathVariable String id) {
        return this.VDCStorageProfileRepository.findById(id).orElse(null);
    }
}
