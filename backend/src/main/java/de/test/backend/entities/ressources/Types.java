package de.test.backend.entities.ressources;

import de.test.backend.entities.base.BaseEntity;
import de.test.backend.entities.prices.Price;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
/**
 * old implementation
 */
// TODO remove completly
public class Types extends BaseEntity {
    String type;
    @Column(length = 4000)
    String fullDescription;
    @ManyToOne
    Price price;

}
