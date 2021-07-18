package com.crawler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Price {
    @Id
    String id;
    Date start;
    Date end;
    String vdcID;
    //name of ressource memory, storage, cpu
    String ressource;
    //eg S,M,L
    String ressourceSize;
    String price;

}
