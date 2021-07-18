package de.thbingenproj.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceDTO {
    String id;
    //resource do not need a vdcID
    String vdcID;
    String orgID;
    String resourceTypeID;
    long startDate;
    long endDate;
}
