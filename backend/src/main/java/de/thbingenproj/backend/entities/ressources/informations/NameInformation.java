package de.test.backend.entities.ressources.informations;

import lombok.*;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
/**
 * Example implementation of ExtraInformation class. Here its possible to store a name for the resource.
 */
public class NameInformation extends ExtraInformation {
    String name;
}
