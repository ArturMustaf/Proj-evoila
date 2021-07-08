package de.thbingenproj.backend.controller.org_network;

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

    @PostMapping("create/orgNetwork")
    public OrgNetwork postOrgNetwork(@RequestBody OrgNetworkDTO orgNetwork){
        return this.orgNetworkRepository.save(modelMapper.map(orgNetwork, OrgNetwork.class));
    }

    @GetMapping("/org-networks/{id}")
    public OrgNetwork getOrgNetworkByID(@PathVariable String id) {
        return this.orgNetworkRepository.findById(id).orElse(null);
    }

    @GetMapping("/vdcs/{id}/org-networks")
    public List<OrgNetwork> getAllOrgNetworkByVdcID(@PathVariable String id) {
        return this.orgNetworkRepository.findAllByVdcID(id);
    }

    @GetMapping("/admin/org-networks")
    public List<OrgNetwork> getAllOrgNetwork() {
        return this.orgNetworkRepository.findAll();
    }
}
