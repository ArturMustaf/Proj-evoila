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
    /**
     * stores a new VDCStorageProfile
     * @param  vdcStorageProfile that should be saved
     * @return the saved vdc object or error
     */
    @PostMapping("/create/vdc-storage-profiles")
    public VDCStorageProfile postVDCStorageProfile(@RequestBody VDCStorageProfileDTO vdcStorageProfile){
        return this.vdcStorageProfileRepository.save(modelMapper.map(vdcStorageProfile, VDCStorageProfile.class));
    }
    /**
     * get the requested VDCStorageProfile
     * @param  id of the VDCStorageProfile
     * @return VDCStorageProfile object or null
     */
    @GetMapping("/vdc-storage-profiles/{id}")
    public VDCStorageProfile getVDCStorageProfiles(@PathVariable String id) {
        return this.vdcStorageProfileRepository.findById(id).orElse(null);
    }
    /**
     * get every stored VDCStorageProfile
     * @return VDCStorageProfile objects
     */
    @GetMapping("/admin/vdc-storage-profiles")
    public List<VDCStorageProfile> getAllVDCStorageProfiles() {
        return this.vdcStorageProfileRepository.findAll();
    }
    /**
     * get every stored VDCStorageProfile from a vdc
     * @param id of the vdc
     * @return VDCStorageProfile objects
     */
    @GetMapping("/vdcs/{id}/vdc-storage-profiles")
    public List<VDCStorageProfile> getAllVDCStorageProfilesByVdcID(@PathVariable String id) {
        return this.vdcStorageProfileRepository.findAllByVdcID(id);
    }
    /**
     * get the latest VDCStorageProfile from a vdc
     * @param  id of the vdc
     * @return VDCStorageProfile object
     */
    @GetMapping("/vdcs/{id}/vdc-storage-profiles/latest")
    public VDCStorageProfile getLatestVDCStorageProfilesByVdcID(@PathVariable String id) {
        return this.vdcStorageProfileRepository.findTopByVdcIDOrderByDateDesc(id);
    }
}
