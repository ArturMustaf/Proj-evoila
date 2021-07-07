package de.thbingenproj.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgUserRoleDTO {
    String href;
    String id;
    String name;
    String type;
    String orgID;
}
