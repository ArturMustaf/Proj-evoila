package de.thbingenproj.backend.entities.ressources;

import de.thbingenproj.backend.entities.base.BaseEntity;
import lombok.*;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Indexed
/**
 * Helper database entry to save resource runtimes, would smaller the execution time of the resource price calculation.
 * Currently not in use.
 */
public class ServerRunTime extends BaseEntity {
    @ManyToOne
    ResourceLog startedEvent;
    @ManyToOne
    ResourceLog stoppedEvent;

}
