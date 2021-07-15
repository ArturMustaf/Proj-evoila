package de.thbingenproj.backend.unused.entities.user.agent;

import de.thbingenproj.backend.unused.entities.base.BaseEntity;
import de.thbingenproj.backend.unused.entities.user.contact_information.ContactInformation;
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
/**
 * Actor or server which can create data or modify data. Agents are for identification of the actor which performed
 * changes in the database
 */

//TODO What are agents used for?
public class Agent extends BaseEntity {
    String name;
    String lastname;
    String identifier;
    @ManyToOne(fetch = FetchType.EAGER)
    AgentType agentType;
    @ManyToOne(fetch = FetchType.EAGER)
    ContactInformation contactInformation;
}
