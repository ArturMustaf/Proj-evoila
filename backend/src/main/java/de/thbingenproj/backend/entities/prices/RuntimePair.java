package de.thbingenproj.backend.entities.prices;

import de.thbingenproj.backend.entities.base.BaseEntity;
import de.thbingenproj.backend.entities.ressources.ResourceEntity;
import de.thbingenproj.backend.entities.ressources.ResourceLog;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RuntimePair extends BaseEntity {
    @ManyToOne
    public ResourceEntity entity;
    @ManyToOne(fetch = FetchType.EAGER)
    public ResourceLog started;
    @ManyToOne(fetch = FetchType.EAGER)
    public ResourceLog stopped;
    @ManyToOne
    public ResourceLog destroyed;
    @ManyToOne
    public Project project;
}
