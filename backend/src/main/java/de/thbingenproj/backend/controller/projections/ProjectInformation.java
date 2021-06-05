package de.thbingenproj.backend.controller.projections;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectInformation {
    String name;
    String description;
    long id;
}
