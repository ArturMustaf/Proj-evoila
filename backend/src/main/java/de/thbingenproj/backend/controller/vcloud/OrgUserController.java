package de.thbingenproj.backend.controller.vcloud;

import de.thbingenproj.backend.entity.OrgUser;
import de.thbingenproj.backend.model.OrgUserDTO;
import de.thbingenproj.backend.repository.OrgUserRepository;
import de.thbingenproj.backend.services.SyncUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrgUserController {
    @Autowired
    OrgUserRepository orgUserRepository;
    @Autowired
    SyncUserService syncUserService;
    @Autowired
    ModelMapper modelMapper;

    /**
     * stores a new orgUser
     * @param  orgUser that should be saved
     * @return the saved orgUserk object or error
     */
    @PostMapping("/create/org-users")
    public OrgUser postOrgUser(@RequestBody OrgUserDTO orgUser){
        var user = this.orgUserRepository.save(modelMapper.map(orgUser, OrgUser.class));
        syncUserService.syncAll();
        return user;
    }
    /**
     * get the requested orgUser
     * @param  id of the orgUser
     * @return orgUser object or null
     */
    @GetMapping("/org-users/{id}")
    public OrgUser getOrgUser(@PathVariable String id) {
        return this.orgUserRepository.findById(id).orElse(null);
    }
    /**
     * get the requested orgUser
     * @param id KeycloakID from the orgUser
     * @return orgUser
     */
    @GetMapping("/org-users/keycloak/{id}")
    public OrgUser getOrgUserByUserID(@PathVariable String id) {
        return this.orgUserRepository.findOrgUserByUserID(id);
    }
    /**
     * synchronize  every orgUser with Keycloak user
     */
    @GetMapping("/org-users/sync")
    public void syncUser(){
        syncUserService.syncAll();
    }
    /**
     * get every stored orgNetwork from an organization
     * @param id of the organization
     * @return orgUser
     */
    @GetMapping("/admin/organizations/{id}/org-users")
    public List<OrgUser> getAllOrgUsersByOrgID(@PathVariable String id) {
        return this.orgUserRepository.findAllByOrgID(id);
    }
    /**
     * get every stored orgUser
     * @return all orgUsers
     */
    @GetMapping("/admin/org-users")
    public List<OrgUser> getAllOrgUsers() {
        return this.orgUserRepository.findAll();
    }

    /**
     * delete an orgUser
     * @param id of the orgUser
     */

    @DeleteMapping("/delete/org-users/{id}")
    public void deleteOrgUser(@PathVariable String id){
        orgUserRepository.deleteById(id);
    }

}
