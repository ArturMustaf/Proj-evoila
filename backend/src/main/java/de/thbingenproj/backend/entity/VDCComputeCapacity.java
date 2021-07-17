package de.thbingenproj.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class VDCComputeCapacity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String cpuUnits;
    long cpuLimit;
    long cpuAllocated;
    long cpuReserved;
    long cpuUsed;
    String memoryUnits;
    long memoryLimit;
    long memoryAllocated;
    long memoryReserved;
    long memoryUsed;
    long date;
    String vdcID;
}
