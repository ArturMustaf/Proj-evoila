package com.crawler.postgres.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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

    @JsonProperty("cpu")
    private void unpackCPU(Map<String, Object> cpu){
        this.cpuUnits = (String) cpu.get("units");
        this.cpuLimit = (int) cpu.get("limit");
        this.cpuAllocated = (int) cpu.get("allocated");
        this.cpuReserved = (int) cpu.get("reserved");
        this.cpuUsed = (int) cpu.get("used");
    }

    @JsonProperty("memory")
    private void unpackMemory(Map<String, Object> memory){
        this.memoryUnits = (String) memory.get("units");
        this.memoryLimit = (int) memory.get("limit");
        this.memoryAllocated = (int) memory.get("allocated");
        this.memoryReserved = (int) memory.get("reserved");
        this.memoryUsed = (int) memory.get("used");
    }
}
