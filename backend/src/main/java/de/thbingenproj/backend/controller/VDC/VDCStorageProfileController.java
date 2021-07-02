package de.thbingenproj.backend.controller.VDC;
import de.thbingenproj.backend.repository.VDCStorageProfileRepository;
import de.thbingenproj.backend.entity.VDCStorageProfile;
import de.thbingenproj.backend.model.VDCStorageProfileDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VDCStorageProfileController {
    @Autowired
    VDCStorageProfileRepository vdcStorageProfileRepository;

    @Autowired
    ModelMapper modelMapper;

    @PutMapping("create/vdcStorageProfile")
    public VDCStorageProfile putVDC(@RequestBody VDCStorageProfileDTO vdcStorageProfile){
        return this.vdcStorageProfileRepository.save(modelMapper.map(vdcStorageProfile, VDCStorageProfile.class));
    }
}
