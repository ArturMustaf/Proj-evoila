package de.thbingenproj.backend.model;


import de.thbingenproj.backend.record.OrgNetworkRecord;
import de.thbingenproj.backend.record.OrgUserRecord;
import de.thbingenproj.backend.record.RoleRecord;
import de.thbingenproj.backend.record.VDCRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
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
