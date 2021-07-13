package com.crawler.postgres.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class PostgresApp {
    @Id
    String id;
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
