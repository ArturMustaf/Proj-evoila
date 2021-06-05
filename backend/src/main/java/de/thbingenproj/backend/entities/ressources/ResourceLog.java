package de.thbingenproj.backend.entities.ressources;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.thbingenproj.backend.entities.base.BaseTimeEntity;
import de.thbingenproj.backend.entities.prices.Project;
import de.thbingenproj.backend.entities.prices.ResourceLogListener;
import de.thbingenproj.backend.entities.ressources.informations.ExtraInformation;
import de.thbingenproj.backend.events.CreationEvent;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
/**
 * Entity to save all Resource belonging events.
 *
 */

public class ResourceLog extends BaseTimeEntity {
    /**
     * @Deprecated
     */
    String resourceId;
    /**
     * Machine which this logs belongs to or null
     */
    @ManyToOne(cascade = CascadeType.ALL)
    ResourceEntity resourceEntity;
    /**
     * Type of the ressource
     */
    @ManyToOne
    ResourceType type;
    /**
     * Quantity of the object: Used for traffic especially
     */
    int quantity;
    /**
     * Event which was triggered
     */
    @ManyToOne
    Event event;
    /**
     * List of extra informations to the resource
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<ExtraInformation> information;
    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    Project project;

    @JsonIgnore
    public Project getProject() {
        return project;
    }

    @JsonIgnore
    public void setProject(Project project) {
        this.project = project;
    }

    @PostPersist
    public void afterSave() {
        ResourceLogListener.applicationEventPublisher.publishEvent(new CreationEvent<ResourceLog>(this));
    }
}
