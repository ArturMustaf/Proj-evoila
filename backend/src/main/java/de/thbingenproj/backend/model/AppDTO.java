package de.thbingenproj.backend.model;

import lombok.Data;

@Data
public class AppDTO {
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
