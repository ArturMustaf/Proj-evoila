package com.crawler.postgres.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class PostgresOSB {
    @Id
    String id;
    String dt;
    String name;
    String org;
    String serviceOffering;
    String servicePlan;
    String space;
}
