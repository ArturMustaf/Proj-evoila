package de.thbingenproj.backend.controller.vcloud;

import de.thbingenproj.backend.entity.OrgNetwork;
import de.thbingenproj.backend.model.OrgNetworkDTO;
import de.thbingenproj.backend.repository.OrgNetworkRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrgNetworkController {

    @Autowired
    OrgNetworkRepository orgNetworkRepository;

    @Autowired
    ModelMapper modelMapper;
    /**
     * stores a new orgNetwork
     * @param  orgNetwork that should be saved
     * @return the saved orgNetwork object or error
     */
    @PostMapping("/create/org-networks")
    public OrgNetwork postOrgNetwork(@RequestBody OrgNetworkDTO orgNetwork){
        return this.orgNetworkRepository.save(modelMapper.map(orgNetwork, OrgNetwork.class));
    }
    /**
     * get the requested orgNetwork
     * @param  id of the orgNetwork
     * @return orgNetwork object or null
     */
    @GetMapping("/org-networks/{id}")
    public OrgNetwork getOrgNetworkByID(@PathVariable String id) {
        return this.orgNetworkRepository.findById(id).orElse(null);
    }
    /**
     * get every stored orgNetwork from a vdc
     * @param id of the vdc
     * @return orgNetwork objects
     */
    @GetMapping("/vdcs/{id}/org-networks")
    public List<OrgNetwork> getAllOrgNetworksByVdcID(@PathVariable String id) {
        return this.orgNetworkRepository.findAllByVdcID(id);
    }
    /**
     * get every stored orgNetwork
     * @return all orgNetwork objects
     */
    @GetMapping("/admin/org-networks")
    public List<OrgNetwork> getAllOrgNetworks() {
        return this.orgNetworkRepository.findAll();
    }
}
