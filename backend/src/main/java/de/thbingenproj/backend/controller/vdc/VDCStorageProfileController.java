package de.thbingenproj.backend.controller.vdc;

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

    @PostMapping("/create/vdc-storage-profiles")
    public VDCStorageProfile postVDCStorageProfile(@RequestBody VDCStorageProfileDTO vdcStorageProfile){
        return this.VDCStorageProfileRepository.save(modelMapper.map(vdcStorageProfile, VDCStorageProfile.class));
    }

    @GetMapping("/vdc-storage-profiles/{id}")
    public VDCStorageProfile getVDCStorageProfiles(@PathVariable String id) {
        return this.VDCStorageProfileRepository.findById(id).orElse(null);
    }

    @GetMapping("/vdcs/{id}/vdc-storage-profiles")
    public List<VDCStorageProfile> getAllVDCStorageProfilesByVdcID(@PathVariable String id) {
        return this.VDCStorageProfileRepository.findAllByVdcID(id);
    }
}
