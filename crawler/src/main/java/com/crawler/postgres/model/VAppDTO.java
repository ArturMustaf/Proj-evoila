package com.crawler.postgres.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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

    @JsonProperty("otherAttributes")
    private void unpackOtherAttributes(Map<String, Object> otherAttributes){
        this.cpuAllocationMhz = (String) otherAttributes.get("cpuAllocationMhz");
        this.storageKB =(String) otherAttributes.get("storageKB");
        this.numberOfVMs = (String) otherAttributes.get("numberOfVMs");
        this.cpuAllocationInMhz = (String) otherAttributes.get("cpuAllocationInMhz");
    }

}
