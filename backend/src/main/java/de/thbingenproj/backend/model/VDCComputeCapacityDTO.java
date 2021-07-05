package de.thbingenproj.backend.model;

import lombok.Data;

@Data
public class VDCComputeCapacityDTO {
    String cpuUnits;
    int cpuLimit;
    int cpuAllocated;
    int cpuReserved;
    int cpuUsed;
    String memoryUnits;
    int memoryLimit;
    int memoryAllocated;
    int memoryReserved;
    int memoryUsed;
    long date;
    String vdcID;

}
