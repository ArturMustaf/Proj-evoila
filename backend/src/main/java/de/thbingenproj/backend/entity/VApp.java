package de.thbingenproj.backend.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class VApp {
    @Id
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
    String vdcHRef;
    String vdcName;
    String vdcID;
    String cpuAllocationMhz;
    String storageKB;
    String numberOfVMs;
    String cpuAllocationInMhz;
}
