package com.crawler.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class VDC {
    @Id
    String id;
    String name;
    String href;
    String tasks;
    String allocationModel;
    String orgID;
//    VDCComputeCapacity computeCapacity;
//    VDCStorageProfile vdcStorageProfiles;
//    VAppRecord resourceEntities;
//    VDCNetworkRecord availableNetworks;
}
