package de.thbingenproj.backend.model;

import lombok.Data;

import javax.persistence.Id;

@Data
public class OSBDTO {
    @Id
    String id;
    long dt;
    String name;
    String org;
    String serviceOffering;
    String servicePlan;
    String space;
}
