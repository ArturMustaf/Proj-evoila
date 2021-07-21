package de.thbingenproj.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class App {
    @Id
    String id;
    String arangoID;
    long diskInMB;
    long dt;
    String guid;
    long instances;
    long memoryInMB;
    String name;
    String org;
    String space;
}
