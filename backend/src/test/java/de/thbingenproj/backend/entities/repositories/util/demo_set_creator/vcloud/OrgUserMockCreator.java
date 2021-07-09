package de.thbingenproj.backend.entities.repositories.util.demo_set_creator.vcloud;

import de.thbingenproj.backend.entity.OrgUser;
import org.springframework.stereotype.Service;

@Service
public class OrgUserMockCreator {

    public OrgUser createOrgUser() {
        OrgUser orgUser = new OrgUser();
        orgUser.setId("id");
        orgUser.setHref("href");
        orgUser.setFullName("fullName");
        orgUser.setIsEnabled(Boolean.TRUE.toString());
        orgUser.setIsLocked(Boolean.TRUE.toString());
        orgUser.setName("name");
        orgUser.setEmailAddress("emailAddress");
        orgUser.setTelephone("telephone");
        orgUser.setIsExternal(Boolean.TRUE.toString());
        orgUser.setIsGroupRole(Boolean.TRUE.toString());
        orgUser.setOrgRoleID("orgRoleID");
        orgUser.setOrgID("orgID");
        return orgUser;
    }
}
