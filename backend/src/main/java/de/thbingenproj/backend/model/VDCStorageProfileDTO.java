package de.thbingenproj.backend.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
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
