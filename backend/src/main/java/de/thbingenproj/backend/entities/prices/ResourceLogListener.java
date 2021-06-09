package de.thbingenproj.backend.entities.prices;

import de.thbingenproj.backend.entities.ressources.ResourceLog;
import de.thbingenproj.backend.events.CreationEvent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;

@Component
public class ResourceLogListener {

    @Getter
    @Setter
    private static ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public void init(ApplicationEventPublisher publisher) {
        ResourceLogListener.setApplicationEventPublisher(publisher);
    }


    @PostPersist
    public void runPairCreation(ResourceLog log) {
        final CreationEvent<ResourceLog> resourceLog = new CreationEvent<>(log);
        applicationEventPublisher.publishEvent(resourceLog);
    }


}
