package de.thbingenproj.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String id;
    //resource do not need a vdcID
    String vdcID;
    //resource must have an orgID
    String orgID;
    String resourceTypeID;
    long startDate;
    long endDate;


}
