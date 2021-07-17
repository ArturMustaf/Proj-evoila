package com.crawler.arango.entity;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Document("spaces")
@Data
public class ArangoSpace {
    @Id
    String id;
    @ArangoId
    String arangoID;
    String guid;
    String name;
    String org;
}
