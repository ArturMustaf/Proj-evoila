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
    /**
     * stores a new vdc
     * @param  vdc that should be saved
     * @return the saved vdc object or error
     */
    @PostMapping("/create/vdcs")
    public VDC postVDC(@RequestBody VDCDTO vdc){
        return this.vdcRepository.save(modelMapper.map(vdc, VDC.class));
    }
    /**
     * get the requested vdc
     * @param  id of the vdc
     * @return vdc object or null
     */
    @GetMapping("/vdcs/{id}")
    public VDC getVDCs(@PathVariable String id) {
        return this.vdcRepository.findById(id).orElse(null);
    }
    /**
     * get every stored vdc
     * @return all vdc objects
     */
    @GetMapping("/admin/vdcs")
    public List<VDC> getAllVDCs() {
        return this.vdcRepository.findAll();
    }
    /**
     * get every stored vdc from an organization
     * @param id of the organization
     * @return vdc objects
     */
    @GetMapping("/organizations/{id}/vdcs")
    public List<VDC> getAllVDCsByOrganizationID(@PathVariable String id) {
        return this.vdcRepository.findAllByOrgID(id);
    }
}
