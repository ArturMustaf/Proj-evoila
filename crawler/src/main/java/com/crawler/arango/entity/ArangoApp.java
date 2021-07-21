package com.crawler.arango.entity;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.Field;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Document("apps")
@Data
public class ArangoApp {
    @Id
    String id;
    @ArangoId
    String arangoID;
    @Field("disk_in_mb")
    long diskInMB;
    String dt;
    String guid;
    long instances;
    @Field("memory_in_mb")
    long memoryInMB;
    String name;
    String org;
    String space;
}
