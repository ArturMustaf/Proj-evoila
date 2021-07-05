package de.thbingenproj.backend.controller.OrgUserController;

import de.thbingenproj.backend.entity.OrgUserRole;
import de.thbingenproj.backend.model.OrgUserRoleDTO;
import de.thbingenproj.backend.repository.OrgUserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrgUserRoleController {
    @Autowired
    OrgUserRoleRepository orgUserRoleRepository;

    @Autowired
    ModelMapper modelMapper;

    @PutMapping("create/orgUserRole")
    public OrgUserRole putOrgUserRole(@RequestBody OrgUserRoleDTO orgUserRole){
        return this.orgUserRoleRepository.save(modelMapper.map(orgUserRole, OrgUserRole.class));
    }

    @GetMapping("orgUser/{id}")
    public OrgUserRole getOrgUserRole(@PathVariable String id) {
        return this.orgUserRoleRepository.findById(id).orElse(null);
    }
}
