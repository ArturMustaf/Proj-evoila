package de.thbingenproj.backend.model;

import lombok.Data;

@Data
public class OSBDTO {
    String id;
    long dt;
    String name;
    String org;
    String serviceoffering;
    String serviceplan;
    String space;
}
