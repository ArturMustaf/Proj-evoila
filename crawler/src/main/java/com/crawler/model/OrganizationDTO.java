package com.crawler.model;

import com.crawler.record.OrgNetworkRecord;
import com.crawler.record.OrgUserRecord;
import com.crawler.record.RoleRecord;
import com.crawler.record.VDCRecord;
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
