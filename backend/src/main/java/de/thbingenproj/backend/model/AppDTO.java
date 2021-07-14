package de.thbingenproj.backend.model;

import lombok.Data;

import javax.persistence.Id;

@Data
public class AppDTO {
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
