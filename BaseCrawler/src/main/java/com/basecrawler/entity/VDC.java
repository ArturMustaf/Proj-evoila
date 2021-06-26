package com.basecrawler.entity;

import com.basecrawler.model.VDCComputeCapacity;
import com.basecrawler.model.VDCStorageProfile;
import com.basecrawler.record.VAppRecord;
import com.basecrawler.record.VDCNetworkRecord;
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
    String type;
    String tasks;
    String allocationModel;
//    VDCComputeCapacity computeCapacity;
//    VDCStorageProfile vdcStorageProfiles;
//    VAppRecord resourceEntities;
//    VDCNetworkRecord availableNetworks;
}
