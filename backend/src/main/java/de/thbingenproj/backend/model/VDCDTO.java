package de.thbingenproj.backend.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VDCDTO {
    String id;
    String name;
    String href;
    String type;
    String tasks;
    String allocationModel;
    String orgID;
    VDCComputeCapacityDTO computeCapacity;

}
