package de.thbingenproj.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResTypeDTO {
    String id;

    //any type of resource e.g. cpu, manhour
    String typeOfResource;

    //short description what this resource is about
    String description;
}
