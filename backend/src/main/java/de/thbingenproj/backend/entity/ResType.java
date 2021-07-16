package de.thbingenproj.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class ResType {
    @Id
    String id;
    //any type of resource e.g. cpu, manhour
    String typeOfResource;
    //short description what this resource is about
    String description;

}
