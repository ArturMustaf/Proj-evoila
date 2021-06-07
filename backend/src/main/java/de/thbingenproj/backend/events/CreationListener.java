package de.thbingenproj.backend.events;

import de.thbingenproj.backend.entities.prices.RuntimePair;
import de.thbingenproj.backend.entities.repositories.ResourceLogRepository;
import de.thbingenproj.backend.entities.repositories.RuntimePairRepository;
import de.thbingenproj.backend.entities.ressources.ResourceLog;
import de.thbingenproj.backend.entities.ressources.types.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Component
public class CreationListener {
    @Autowired
    RuntimePairRepository runtimePairRepository;
    private final HashMap<Long, List<ResourceLog>> logTest = new HashMap<>();
    LinkedList<ResourceLog> events = new LinkedList<>();
    @Autowired
    ResourceLogRepository resourceLogRepository;
    private ResourceLog retriedFor;
    private int retriedAmount = 0;

    @Scheduled(fixedDelay = 1000)
    public void createRuntimePairs() {
        while (!events.isEmpty()) {
            ResourceLog createdlog = events.removeFirst();

            createdlog = checkRetried(createdlog);

            ResourceLog log = resourceLogRepository.findById(createdlog.getId()).orElse(null);
            if (log == null) {
                events.addFirst(createdlog);
                retriedFor = createdlog;
                retriedAmount += 1;
                return;
            }

            if (log.getEvent() != null && log.getEvent().getEventType() == EventType.STARTED) {
                var runtimePair = new RuntimePair();
                runtimePair.started = log;
                runtimePair.entity = log.getResourceEntity();
                runtimePair.project = log.getProject();
                runtimePairRepository.save(runtimePair);
            } else if (log.getEvent() != null && log.getEvent().getEventType() == EventType.STOPPED) {
                List<RuntimePair> runtimePair = runtimePairRepository.findRuntimePairByEntityIdAndStopped(log.getResourceEntity().getId());
                if (runtimePair == null || runtimePair.isEmpty()) {
                    events.addFirst(createdlog);
                    retriedFor = createdlog;
                    retriedAmount += 1;
                    return;
                }
                runtimePair.get(0).stopped = log;
                runtimePairRepository.save(runtimePair.get(0));

            }
        }
    }

    public ResourceLog checkRetried(ResourceLog createdlog){
        if (retriedAmount == 3 && retriedFor.equals(createdlog)) {
            retriedAmount = 0;
            createdlog = events.removeFirst();
        } else if (retriedFor != null && !retriedFor.equals(createdlog)) {
            retriedAmount = 0;
        }
        return createdlog;
    }

    @EventListener()
    public void createResourceLog(CreationEvent<ResourceLog> creationEvent) {
        ResourceLog log = creationEvent.getEntity();

        if (log.getEvent() != null && log.getEvent().getEventType() == EventType.STOPPED || log.getEvent().getEventType() == EventType.STARTED) {
            if (!logTest.containsKey(log.getResourceEntity().getId())) {
                logTest.put(log.getResourceEntity().getId(), new ArrayList<>());
            }
            logTest.get(log.getResourceEntity().getId()).add(log);
            events.addLast(log);
        }
    }
}
