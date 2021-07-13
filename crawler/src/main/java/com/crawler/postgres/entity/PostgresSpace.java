package com.crawler.postgres.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class PostgresSpace {
    @Id
    String id;
    String arangoID;
    String guid;
    String name;
    String org;
}
