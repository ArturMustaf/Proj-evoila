package de.test.backend.entities.ressources.informations;

import de.test.backend.entities.ressources.ResourceLog;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
/**
 * Currently not in use
 * Idea was to use it to define which started event and stopped event belong together
 */
public class LinkedWithInformation extends ExtraInformation {
    @ManyToOne()
    ResourceLog resourceLog;

    public LinkedWithInformation(ResourceLog resourceLog) {
        this.resourceLog = resourceLog;
    }
}
