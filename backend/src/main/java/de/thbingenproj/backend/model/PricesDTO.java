package de.thbingenproj.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricesDTO {
    String id;
    //start date
    long startDate;
    //end date
    long endDate;
    //name of ressource e.g. memory, storage, cpu, manhour
    String resourceID;
    //eg S,M,L
    String resourceSize;
    //price in cent stored
    long priceInCent;
    String vdcID;
    String orgID;

}
