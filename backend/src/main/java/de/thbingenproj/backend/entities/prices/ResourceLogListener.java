package de.thbingenproj.backend.entities.prices;

import de.thbingenproj.backend.entities.ressources.ResourceLog;
import de.thbingenproj.backend.events.CreationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;

@Component
public class ResourceLogListener {

    public static ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public void init(ApplicationEventPublisher publisher) {
        ResourceLogListener.applicationEventPublisher = publisher;
    }


    @PostPersist
    public void runPairCreation(ResourceLog log) {
        System.out.println(log);
        final CreationEvent<ResourceLog> resourceLog = new CreationEvent<>(log);
        applicationEventPublisher.publishEvent(resourceLog);
    }


}
