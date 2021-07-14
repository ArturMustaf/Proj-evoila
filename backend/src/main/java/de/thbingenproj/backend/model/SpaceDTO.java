package de.thbingenproj.backend.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class SpaceDTO {
    @Id
    String id;
    String arangoID;
    String guid;
    String name;
    String org;
}
