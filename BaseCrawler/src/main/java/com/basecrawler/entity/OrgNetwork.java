package com.basecrawler.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class OrgNetwork {
    String href;
    @Id
    String id;
    String name;
    String type;
}
