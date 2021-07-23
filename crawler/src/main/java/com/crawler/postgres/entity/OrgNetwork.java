package com.crawler.postgres.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrgNetwork {
    @Id
    String id;
    String href;
    String name;
    String orgID;
    String vdcID;
}
