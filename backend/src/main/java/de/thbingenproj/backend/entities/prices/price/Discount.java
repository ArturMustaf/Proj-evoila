package de.thbingenproj.backend.entities.prices.price;

import de.thbingenproj.backend.entities.ressources.ResourceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
/**
 * Currently not used => Basic Idea is to define a abstract of this class to apply discounts to specific projects.
 *
 */
public abstract class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    @ManyToOne
    ResourceType type;
}
