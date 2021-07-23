package com.crawler.postgres.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgNetworkDTO {
    String id;
    String href;
    String name;
    String type;
    String orgID;
}
