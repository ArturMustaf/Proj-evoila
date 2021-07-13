package com.crawler.arango.entity;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Document("apps")
@Data
public class App {
    @Id
    String id;
    @ArangoId
    String arangoID;
    String diskInMb;
    String dt;
    String guid;
    String instances;
    String memoryInMb;
    String name;
    String org;
    String space;
}
