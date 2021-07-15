package de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.logging_resources;

import de.thbingenproj.backend.unused.entities.repositories.EventRepository;
import de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.dataset.DataCreator;
import de.thbingenproj.backend.unused.entities.ressources.Event;
import de.thbingenproj.backend.unused.entities.ressources.types.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
/**
 * Class to generate testing data for Events
 */
public class EventMockCreator {
    @Autowired
    DataCreator creator;
    Map<EventType, Event> events = new HashMap<>();

    private void putEvent(Event e){
        events.put(e.getEventType(), e);
    }

    private Event getEvent(EventType type){
        return events.get(type);
    }


    EventMockCreator(@Autowired EventRepository repository, @Autowired DataCreator creator){
        this.creator = creator;
        Event startedEvent;
        Event stoppedEvent;
        repository.findAll().forEach(x -> {
            if (x.getEventType() == EventType.STARTED || x.getEventType() == EventType.STOPPED)
                putEvent(x);
        });
        startedEvent = getEvent(EventType.STARTED);
        if(startedEvent == null) {
            startedEvent = new Event();
            startedEvent.setEventType(EventType.STARTED);
            startedEvent.setEventDescription("Started");
            events.put(EventType.STARTED, startedEvent);
            repository.saveAll(Arrays.asList(startedEvent));
        }

        stoppedEvent = getEvent(EventType.STOPPED);
        if(stoppedEvent == null) {
            stoppedEvent = new Event();
            stoppedEvent.setEventType(EventType.STOPPED);
            stoppedEvent.setEventDescription("Stopped");
            events.put(EventType.STOPPED, stoppedEvent);
            repository.saveAll(Arrays.asList(stoppedEvent));
        }


    }

    public Event createEvent(){
        Event event = new Event();
        event.setEventDescription("");

        return event;
    }

    public Event createStartedEvent(){
        return events.get(EventType.STARTED);
    }
    public Event createStoppedEvent(){

        return events.get(EventType.STOPPED);
    }

}
