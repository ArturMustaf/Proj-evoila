package de.thbingenproj.backend.controller.OrgUserController;

import de.thbingenproj.backend.entity.OrgUser;
import de.thbingenproj.backend.model.OrgUserDTO;
import de.thbingenproj.backend.repository.OrgUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrgUserController {
    @Autowired
    OrgUserRepository orgUserRepository;

    @Autowired
    ModelMapper modelMapper;

    @PutMapping("create/orgUser")
    public OrgUser putOrgUser(@RequestBody OrgUserDTO orgUser){
        return this.orgUserRepository.save(modelMapper.map(orgUser, OrgUser.class));
    }

    //ToDo implement deleteOrgUser
    @DeleteMapping("delete/orgUser/{id}")
    public String deleteOrgUser(@PathVariable("id") String id) {
        throw new UnsupportedOperationException("delete/orgUser not implemented yet.");
    }

    @GetMapping("orgUser/{id}")
    public OrgUser getOrgUser(@PathVariable String id) {
        return this.orgUserRepository.findById(id).orElse(null);
    }
}
