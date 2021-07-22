package de.thbingenproj.backend.controller.vcloud;

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
    /**
     * stores a new orgUserRole
     * @param  orgUserRole that should be saved
     * @return the saved orgUserRole object or error
     */
    @PostMapping("/create/org-user-roles")
    public OrgUserRole postOrgUserRole(@RequestBody OrgUserRoleDTO orgUserRole){
        return this.orgUserRoleRepository.save(modelMapper.map(orgUserRole, OrgUserRole.class));
    }
    /**
     * get the requested orgUserRole
     * @param  id of the orgUserRole
     * @return orgUserRole object or null
     */
    @GetMapping("/org-user-roles/{id}")
    public OrgUserRole getOrgUserRole(@PathVariable String id) {
        return this.orgUserRoleRepository.findById(id).orElse(null);
    }
    /**
     * get every stored orgUserRole from an organization
     * @param id of the organization
     * @return orgUserRole objects
     */
    @GetMapping("/admin/organizations/{id}/org-user-roles")
    public List<OrgUserRole> getAllOrgUserRolesByOrgID(@PathVariable String id) {
        return this.orgUserRoleRepository.findAllByOrgID(id);
    }
    /**
     * get every stored orgUserRole
     * @return all orgUserRole objects
     */
    @GetMapping("/admin/org-user-roles")
    public List<OrgUserRole> getAllOrgUserRoles() {
        return this.orgUserRoleRepository.findAll();
    }
}
