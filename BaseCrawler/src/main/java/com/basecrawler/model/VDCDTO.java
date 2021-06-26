package com.basecrawler.model;

import com.basecrawler.entity.OrgNetwork;
import com.basecrawler.record.VAppRecord;
import com.basecrawler.record.VDCNetworkRecord;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VDCDTO {
    String id;
    String name;
    String href;
    String type;
    String tasks;
    String allocationModel;
    VDCComputeCapacity computeCapacity;
    VDCStorageProfile vdcStorageProfiles;
    VAppRecord resourceEntities;
    VDCNetworkRecord availableNetworks;
}
