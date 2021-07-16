package de.thbingenproj.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@Entity
public class Prices {
    @Id
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

}
