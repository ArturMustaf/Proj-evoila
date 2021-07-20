package de.thbingenproj.backend.controller.vcloud;

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
    VDCStorageProfileRepository vdcStorageProfileRepository;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/create/vdc-storage-profiles")
    public VDCStorageProfile postVDCStorageProfile(@RequestBody VDCStorageProfileDTO vdcStorageProfile){
        return this.vdcStorageProfileRepository.save(modelMapper.map(vdcStorageProfile, VDCStorageProfile.class));
    }

    @GetMapping("/vdc-storage-profiles/{id}")
    public VDCStorageProfile getVDCStorageProfiles(@PathVariable String id) {
        return this.vdcStorageProfileRepository.findById(id).orElse(null);
    }

    @GetMapping("/admin/vdc-storage-profiles")
    public List<VDCStorageProfile> getAllVDCStorageProfiles() {
        return this.vdcStorageProfileRepository.findAll();
    }
    @GetMapping("/vdcs/{id}/vdc-storage-profiles")
    public List<VDCStorageProfile> getAllVDCStorageProfilesByVdcID(@PathVariable String id) {
        return this.vdcStorageProfileRepository.findAllByVdcID(id);
    }
}
