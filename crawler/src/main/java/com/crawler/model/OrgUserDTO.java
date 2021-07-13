package com.crawler.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgUserDTO {
    String href;
    String id;
    String type;
    String fullName;
    String isEnabled;
    String isLocked;
    String name;
    String emailAddress;
    String telephone;
    String isExternal;
    String isGroupRole;
    String orgRoleID;
    String orgID;
    OrgUserRoleDTO role;
}
