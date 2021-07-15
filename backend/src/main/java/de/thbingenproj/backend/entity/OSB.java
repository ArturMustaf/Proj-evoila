package de.thbingenproj.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class OSB {
    @Id
    String id;
    long dt;
    String name;
    String org;
    String serviceoffering;
    String serviceplan;
    String space;
}
