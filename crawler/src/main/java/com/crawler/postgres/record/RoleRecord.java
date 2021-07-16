package com.crawler.postgres.record;

import com.crawler.postgres.model.OrgUserRoleDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleRecord {
    @JsonAlias("roleReference")
    ArrayList<OrgUserRoleDTO> orgUserRoleRecord;
}
