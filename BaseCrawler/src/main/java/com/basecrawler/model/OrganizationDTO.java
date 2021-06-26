package com.basecrawler.model;

import com.basecrawler.record.OrgUserRecord;
import com.basecrawler.record.RoleRecord;
import com.basecrawler.record.VDCRecord;
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
    String tasks;
    String name;
    String fullName;
    String isEnabled;
    OrgUserRecord users;
    VDCRecord vdcs;
    RoleRecord roleReferences;
}
