package de.thbingenproj.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Data
@Entity
public class Prices {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    //redudant to Resource but it is faster then asked from resources
    //resource do not need a vdcID
    String vdcID;
    //resource must have an orgID
    String orgID;
}
