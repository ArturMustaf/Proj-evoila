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
    long start;
    //end date
    long end;
    //name of ressource e.g. memory, storage, cpu, manhour
    String resourceID;
    //eg S,M,L
    String resourceSize;
    //price in cent stored
    long priceInCent;

}
