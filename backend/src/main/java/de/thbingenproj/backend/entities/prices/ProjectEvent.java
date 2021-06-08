package de.thbingenproj.backend.entities.prices;

import de.thbingenproj.backend.entities.base.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
/**
 * Projectevent is currently not implemented
 * Idea to save project events like project created, description changed, closed
 */
public class ProjectEvent extends BaseEntity {
    String eventType;
    @Column(length = 4000)
    String eventDescription;
    String change;
}
