package com.basecrawler.record;

import com.basecrawler.model.OrganizationDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgRecord {
    ArrayList<OrganizationDTO> record;
}
