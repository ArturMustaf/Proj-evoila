package de.thbingenproj.backend.controller.vapp;


import de.thbingenproj.backend.entity.VApp;
import de.thbingenproj.backend.model.VAppDTO;
import de.thbingenproj.backend.repository.VAppRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class vAppController {
    @Autowired
    VAppRepository vAppRepository;

    @Autowired
    ModelMapper modelMapper;


    @PostMapping("/create/vapps")
    public VApp postVApp(@RequestBody VAppDTO vAppDTO){
        return this.vAppRepository.save(modelMapper.map(vAppDTO, VApp.class));
    }

    @GetMapping("/vapps/{id}")
    public VApp getVApp(@PathVariable String id) {
        return this.vAppRepository.findById(id).orElse(null);
    }

    @GetMapping("/vdcs/{id}/vapps")
    public List<VApp> getAllVAppsByVdcID(@PathVariable String id) {
        return this.vAppRepository.findAllByVdcID(id);
    }

    @GetMapping("/admin/vapps")
    public List<VApp> getAllVApps() {
        return this.vAppRepository.findAll();
    }
}
