package de.thbingenproj.backend.model;

import lombok.Data;

@Data
public class AppDTO {
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
