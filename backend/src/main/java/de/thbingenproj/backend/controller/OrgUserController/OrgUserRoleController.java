package de.thbingenproj.backend.controller.OrgUserController;

import de.thbingenproj.backend.entity.OrgUser;
import de.thbingenproj.backend.entity.OrgUserRole;
import de.thbingenproj.backend.model.OrgUserRoleDTO;
import de.thbingenproj.backend.repository.OrgUserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrgUserRoleController {
    @Autowired
    OrgUserRoleRepository orgUserRoleRepository;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("create/org-user-roles")
    public OrgUserRole postOrgUserRole(@RequestBody OrgUserRoleDTO orgUserRole){
        return this.orgUserRoleRepository.save(modelMapper.map(orgUserRole, OrgUserRole.class));
    }

    @GetMapping("/org-user-roles/{id}")
    public OrgUserRole getOrgUserRole(@PathVariable String id) {
        return this.orgUserRoleRepository.findById(id).orElse(null);
    }

    @GetMapping("/admin/organizations/{id}/org-user-roles")
    public List<OrgUserRole> getAllOrgUserRoleByOrgID(@PathVariable String id) {
        return this.orgUserRoleRepository.findAllByOrgID(id);
    }

    @GetMapping("/admin/org-user-roles")
    public List<OrgUserRole> getAllOrgUserRole() {
        return this.orgUserRoleRepository.findAll();
    }
}
