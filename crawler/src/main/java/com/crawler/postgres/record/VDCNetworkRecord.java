package com.crawler.postgres.record;

import com.crawler.postgres.model.OrgNetworkDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VDCNetworkRecord {
    ArrayList<OrgNetworkDTO> network;
}
