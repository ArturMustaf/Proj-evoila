package com.crawler.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceDTO {
    String id;
    Date start;
    Date end;
    String vdcID;
    //name of ressource memory, storage, cpu
    String ressource;
    //eg S,M,L
    String ressourceSize;
    String price;
    VDCDTO vdc;

}
