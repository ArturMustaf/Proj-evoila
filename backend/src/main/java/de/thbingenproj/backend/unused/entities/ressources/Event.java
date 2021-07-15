package de.thbingenproj.backend.unused.entities.ressources;

import de.thbingenproj.backend.unused.entities.base.BaseEntity;
import de.thbingenproj.backend.unused.entities.ressources.types.EventType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
/**
 * Table of the existing events and their descriptions.
 * The eventtype if for behavioural differentiation
 */

//TODO not in use
public class Event extends BaseEntity {
    @Enumerated(EnumType.STRING)
    EventType eventType;
    @Column(length = 4000)
    String eventDescription;
}
