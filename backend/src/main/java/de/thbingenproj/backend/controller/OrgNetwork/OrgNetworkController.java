package de.thbingenproj.backend.controller.OrgNetwork;

import de.thbingenproj.backend.entity.OrgNetwork;
import de.thbingenproj.backend.entity.OrgUser;
import de.thbingenproj.backend.entity.Organization;
import de.thbingenproj.backend.model.OrgNetworkDTO;
import de.thbingenproj.backend.model.OrgUserDTO;
import de.thbingenproj.backend.model.OrganizationDTO;
import de.thbingenproj.backend.repository.OrgNetworkRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrgNetworkController {

    @Autowired
    OrgNetworkRepository orgNetworkRepository;

    @Autowired
    ModelMapper modelMapper;

    @PutMapping("create/orgNetwork")
    public OrgNetwork putOrgNetwork(@RequestBody OrgNetworkDTO orgNetwork){
        return this.orgNetworkRepository.save(modelMapper.map(orgNetwork, OrgNetwork.class));
    }

    //ToDo implement deleteOrgNetwork
    @DeleteMapping("delete/orgNetwork/{id}")
    public String deleteOrgNetwork(@PathVariable("id") String id) {
        throw new UnsupportedOperationException("delete/orgNetwork not implemented yet.");
    }

    @GetMapping("orgNetwork/{id}")
    public OrgNetwork getOrgNetwork(@PathVariable String id) {
        return this.orgNetworkRepository.findById(id).orElse(null);
    }
}
