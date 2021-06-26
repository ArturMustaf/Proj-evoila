package com.basecrawler.entity;

import com.basecrawler.model.OrgUserRoleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrgUser {
    @Id
    String id;
    String href;
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
    String roleID;
    String orgID;
}
