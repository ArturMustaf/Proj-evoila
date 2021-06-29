package com.crawler.entity;

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
    String fullName;
    String isEnabled;
    String isLocked;
    String name;
    String emailAddress;
    String telephone;
    String isExternal;
    String isGroupRole;
    String OrgRoleID;
    String orgID;
}
