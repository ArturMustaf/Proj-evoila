package de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud;

import de.thbingenproj.backend.entity.OrgUser;
import de.thbingenproj.backend.entity.OrgUserRole;
import org.springframework.stereotype.Service;

@Service
public class OrgUserRoleMockCreator {

    public OrgUserRole createOrgUserRole() {
        OrgUserRole orgUserRole = new OrgUserRole();
        orgUserRole.setId("id");
        orgUserRole.setHref("href");
        orgUserRole.setName("name");
        orgUserRole.setOrgID("orgID");
        return orgUserRole;
    }
}
