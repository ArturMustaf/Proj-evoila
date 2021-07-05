package de.thbingenproj.backend.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;


@Data
public class VAppDTO {
    String id;
    String href;
    String name;
    String description;
    String creationDate;
    String isAutoNature;
    String isBusy;
    String isDeployed;
    String isEnabled;
    String isExpired;
    String isInMaintenanceMode;
    String isPublic;
    String memoryAllocationMB;
    String numberOfCpus;
    String ownerName;
    String status;
    String totalStorageAllocatedMb;
    @JsonAlias("vdc")
    String vdcHRef;
    String vdcName;
    String vdcID;
    String cpuAllocationMhz;
    String storageKB;
    String numberOfVMs;
    String cpuAllocationInMhz;

}
