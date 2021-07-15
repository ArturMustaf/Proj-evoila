package de.thbingenproj.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

//https://code.vmware.com/apis/72/vmware-cloud-director/doc/doc/types/VdcStorageProfileType.html
//ToDo check datatypes, eg units as a long value

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VDCStorageProfileDTO {
    String id;
    String href;
    String name;
    String type;
    String vdcID;
    long date;
    String enabled;
    String units;
    String maxLimit;
    String storageUsedMB;
    String iopsAllocated;
}
