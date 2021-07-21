package de.thbingenproj.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VDCComputeCapacityDTO {
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
    String vdcNumber;
}
