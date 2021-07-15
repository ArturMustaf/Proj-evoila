package de.thbingenproj.backend.unused.entities.prices;

import de.thbingenproj.backend.unused.entities.base.BaseEntity;
import de.thbingenproj.backend.unused.entities.ressources.ResourceEntity;
import de.thbingenproj.backend.unused.entities.ressources.ResourceLog;
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
