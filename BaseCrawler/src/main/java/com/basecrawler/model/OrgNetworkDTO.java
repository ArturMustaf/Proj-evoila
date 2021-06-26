package com.basecrawler.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgNetworkDTO {
    String href;
    String id;
    String name;
    String type;
}
