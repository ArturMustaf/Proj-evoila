package de.thbingenproj.backend.unused.entities.rights;

import de.thbingenproj.backend.unused.entities.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
/**
 * Describes every permission which can be granted with descriptions.
 */
public class PermissionType extends BaseEntity {
    /**
     * Short description of the permission
     */
    String shortDescription;
    /**
     * complete description of the permission and which rights it grants
     */
    String fullDescription;
}
