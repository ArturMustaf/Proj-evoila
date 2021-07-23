package de.thbingenproj.backend.unused.entities.rights;

import de.thbingenproj.backend.unused.entities.base.BaseEntity;
import de.thbingenproj.backend.unused.entities.prices.Project;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
/**
 * currently not in use
 * User permission definition
 * always specific to a project to allow granular permissions
 * permission type defines which rights should be granted
 */
public class Permission extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    Project project;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    PermissionType permissionType;
}
