package com.crawler.postgres.entity;

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
