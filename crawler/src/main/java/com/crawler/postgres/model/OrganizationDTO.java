package com.crawler.postgres.model;

import com.crawler.postgres.record.OrgNetworkRecord;
import com.crawler.postgres.record.OrgUserRecord;
import com.crawler.postgres.record.RoleRecord;
import com.crawler.postgres.record.VDCRecord;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganizationDTO {
    String href;
    String type;
    String id;
    String description;
    String name;
    String fullName;
    String isEnabled;
    OrgUserRecord users;
    VDCRecord vdcs;
    RoleRecord roleReferences;
    OrgNetworkRecord networks;
}
