package com.crawler.postgres.model;

import com.crawler.postgres.record.StorageProfileRecord;
import com.crawler.postgres.record.VDCNetworkRecord;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VDCDTO {
    String id;
    String name;
    String href;
    String type;
    String tasks;
    String allocationModel;
    String orgID;
    VDCComputeCapacityDTO computeCapacity;
    StorageProfileRecord vdcStorageProfiles;
    VDCNetworkRecord availableNetworks;
}
