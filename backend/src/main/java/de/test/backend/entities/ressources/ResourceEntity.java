package de.test.backend.entities.ressources;

import de.test.backend.entities.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
/**
 * Used for external identifier of machines and mapping to local id
 */
public class ResourceEntity extends BaseEntity {
    String resourceID;
}
