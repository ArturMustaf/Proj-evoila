package com.crawler.postgres.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VDCStorageProfileDTO {
    String id;
    String href;
    String name;
    String type;
    String vdcID;
    long date;
    String enabled;
    String units;
    @JsonAlias("limit")
    String maxLimit;
    String storageUsedMB;
    String iopsAllocated;
}
