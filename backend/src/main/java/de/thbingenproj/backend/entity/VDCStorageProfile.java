package de.thbingenproj.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class VDCStorageProfile {
    @Id
    String id;
    String href;
    String name;
    String vdcID;
    long date;
    String enabled;
    String units;
    String maxLimit;
    String storageUsedMB;
    String iopsAllocated;
}
