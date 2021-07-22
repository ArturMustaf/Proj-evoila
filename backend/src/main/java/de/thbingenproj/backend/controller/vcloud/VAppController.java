package de.thbingenproj.backend.controller.vcloud;


import de.thbingenproj.backend.entity.VApp;
import de.thbingenproj.backend.model.VAppDTO;
import de.thbingenproj.backend.repository.VAppRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VAppController {
    @Autowired
    VAppRepository vAppRepository;

    @Autowired
    ModelMapper modelMapper;

    /**
     * stores a new vApp
     * @param  vApp that should be saved
     * @return the saved vApp object or error
     */

    @PostMapping("/create/vapps")
    public VApp postVApp(@RequestBody VAppDTO vApp){
        return this.vAppRepository.save(modelMapper.map(vApp, VApp.class));
    }
    /**
     * get the requested vApp
     * @param  id of the vApp
     * @return vApp object or null
     */
    @GetMapping("/vapps/{id}")
    public VApp getVApp(@PathVariable String id) {
        return this.vAppRepository.findById(id).orElse(null);
    }
    /**
     * get every stored vApp from a vdc
     * @param id of the vdc
     * @return vApp objects
     */
    @GetMapping("/vdcs/{id}/vapps")
    public List<VApp> getAllVAppsByVdcID(@PathVariable String id) {
        return this.vAppRepository.findAllByVdcID(id);
    }
    /**
     * get every stored vApp
     * @return all vApp objects
     */
    @GetMapping("/admin/vapps")
    public List<VApp> getAllVApps() {
        return this.vAppRepository.findAll();
    }
}
