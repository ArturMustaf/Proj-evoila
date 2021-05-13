package de.test.backend.entities.user.agent;

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
 * AgentType defines the type of the actor, either server or company employee
 */
public class AgentType extends BaseEntity {
    String description;
    String shortDescription;
}
