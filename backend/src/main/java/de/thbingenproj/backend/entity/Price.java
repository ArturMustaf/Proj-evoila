package de.thbingenproj.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;


@Data
@Entity
public class Price {
    @Id
    long id;
    Date start;
    Date end;
    String vdcID;
    //name of ressource memory, storage, cpu
    String ressource;
    //eg S,M,L
    String ressourceSize;
    String price;

}
