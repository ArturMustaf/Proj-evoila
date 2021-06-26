package com.basecrawler.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgUserDTO {
    String href;
    String id;
    String type;
    String deployedVMQuota;
    String fullName;
    String isEnabled;
    String isLdapUser;
    String isLocked;
    String name;
    String numberOfDeployedVMs;
    String numberOfStoredVMs;
    String roleNames;
    String storedVMQuota;
    String emailAddress;
    String telephone;
    String isExternal;
    String isGroupRole;
    OrgUserRoleDTO role;
}
