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
    String diskInMb;
    long dt;
    String guid;
    String instances;
    String memoryInMb;
    String name;
    String org;
    String space;
}
