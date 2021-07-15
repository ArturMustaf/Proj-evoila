package de.thbingenproj.backend.controller.org_user;

import de.thbingenproj.backend.entity.OrgUser;
import de.thbingenproj.backend.model.OrgUserDTO;
import de.thbingenproj.backend.repository.OrgUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrgUserController {
    @Autowired
    OrgUserRepository orgUserRepository;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/create/org-users")
    public OrgUser postOrgUser(@RequestBody OrgUserDTO orgUser){
        return this.orgUserRepository.save(modelMapper.map(orgUser, OrgUser.class));
    }

    @GetMapping("/org-users/{id}")
    public OrgUser getOrgUser(@PathVariable String id) {
        return this.orgUserRepository.findById(id).orElse(null);
    }

    @GetMapping("/admin/organizations/{id}/org-users")
    public List<OrgUser> getAllOrgUserByOrgID(@PathVariable String id) {
        return this.orgUserRepository.findAllByOrgID(id);
    }

    @GetMapping("/admin/org-users")
    public List<OrgUser> getAllOrgUser() {
        return this.orgUserRepository.findAll();
    }
}
