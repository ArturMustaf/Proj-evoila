package de.test.backend.entities.prices;

import de.test.backend.entities.ressources.ResourceLog;
import de.test.backend.events.CreationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;

@Component
public class ResourceLogListener {

    //@Autowired
    //ResourceLogRepository resourceLogRepository;

    public static ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public void init(ApplicationEventPublisher publisher) {
        ResourceLogListener.applicationEventPublisher = publisher;
    }


    @PostPersist
    public void runPairCreation(ResourceLog log) {
        System.out.println(log);
        final CreationEvent<ResourceLog> resourceLog = new CreationEvent<ResourceLog>(log);
        applicationEventPublisher.publishEvent(resourceLog);
    }


}
