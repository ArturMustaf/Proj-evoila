package com.basecrawler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Organization {
    @Id
    String id;
    String href;
    String type;
    String description;
    String tasks;
    String name;
    String fullName;
    String isEnabled;
}
