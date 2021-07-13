package com.crawler.arango.entity;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Document("osbs")
@Data
public class OSB {
    @Id
    String id;
    @ArangoId
    String arangoID;
    String dt;
    String name;
    String org;
    String serviceOffering;
    String servicePlan;
    String space;
}
