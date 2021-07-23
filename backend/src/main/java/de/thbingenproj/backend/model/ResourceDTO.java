package de.thbingenproj.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceDTO {
    long id;
    //resource do not need a vdcID
    String vdcID;
    String orgID;
    long resourceTypeID;
    long startDate;
    long endDate;
}
