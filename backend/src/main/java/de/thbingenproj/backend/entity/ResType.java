package de.thbingenproj.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class ResType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    //any type of resource e.g. cpu, manhour
    String typeOfResource;
    //short description what this resource is about
    String description;

}
