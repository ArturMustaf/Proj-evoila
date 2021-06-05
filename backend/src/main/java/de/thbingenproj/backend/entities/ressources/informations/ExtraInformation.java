package de.thbingenproj.backend.entities.ressources.informations;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
/**
 *  Currently not in use. Basis class to add extra information to resourcelogs
 */
public abstract class ExtraInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
}
