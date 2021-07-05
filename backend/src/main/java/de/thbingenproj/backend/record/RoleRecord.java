package de.thbingenproj.backend.record;

import de.thbingenproj.backend.model.OrgUserRoleDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleRecord {
    ArrayList<OrgUserRoleDTO> orgUserRoleRecord;
}
