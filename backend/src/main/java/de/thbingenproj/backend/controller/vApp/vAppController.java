package de.thbingenproj.backend.controller.vApp;


import de.thbingenproj.backend.entity.VApp;
import de.thbingenproj.backend.model.VAppDTO;
import de.thbingenproj.backend.repository.OrgUserRoleRepository;
import de.thbingenproj.backend.repository.VAppRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class vAppController {
    @Autowired
    VAppRepository vAppRepository;

    @Autowired
    ModelMapper modelMapper;


    @PutMapping("create/vAPP")
    public VApp putVAPP(@RequestBody VAppDTO vAppDTO){
        return this.vAppRepository.save(modelMapper.map(vAppDTO, VApp.class));
    }

}
