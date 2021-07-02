package de.thbingenproj.backend.controller.OrgNetwork;

import de.thbingenproj.backend.entity.OrgNetwork;
import de.thbingenproj.backend.entity.OrgUser;
import de.thbingenproj.backend.model.OrgNetworkDTO;
import de.thbingenproj.backend.model.OrgUserDTO;
import de.thbingenproj.backend.repository.OrgNetworkRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrgNetworkController {

    @Autowired
    OrgNetworkRepository orgNetworkRepository;

    @Autowired
    ModelMapper modelMapper;

    @PutMapping("create/orgNetwrok")
    public OrgNetwork putOrgNetwork(@RequestBody OrgNetworkDTO orgNetwork){
        return this.orgNetworkRepository.save(modelMapper.map(orgNetwork, OrgNetwork.class));
    }
}
