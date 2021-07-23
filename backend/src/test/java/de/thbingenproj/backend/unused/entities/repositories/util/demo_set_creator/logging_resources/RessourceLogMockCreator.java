package de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.logging_resources;

import de.thbingenproj.backend.unused.entities.repositories.ResourceEntityRepository;
import de.thbingenproj.backend.unused.entities.repositories.ResourceLogRepository;
import de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.dataset.DataCreator;
import de.thbingenproj.backend.unused.entities.ressources.ResourceEntity;
import de.thbingenproj.backend.unused.entities.ressources.ResourceLog;
import de.thbingenproj.backend.unused.entities.ressources.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
/**
 * ResourceLog mock creator to create real looking datasets
 */
public class RessourceLogMockCreator {
    @Autowired
    ResourceTypeMockCreator resourceTypeMockCreator;
    @Autowired
    EventMockCreator eventMockCreator;
    @Autowired
    DataCreator dataCreator;
    @Autowired
    ResourceLogRepository logRepository;
    @Autowired
    ResourceEntityRepository resourceEntityRepository;

    private int generateResourceQuantity(String resourceDescription) {
        if (resourceDescription.startsWith("VM"))
            return 1;
        else if (resourceDescription.startsWith("Static IP"))
            return 1;
        else if (resourceDescription.startsWith("Traffic"))
            return dataCreator.getRandom(0, 300);
        else if (resourceDescription.startsWith("Storage"))
            return dataCreator.getRandom(0, 1000);
        return -1;

    }

    @Transactional
    public List<ResourceLog> createRessourceLog() {
        List<ResourceLog> resourceLogs = new ArrayList<>();
        // 50 * 15
        for (int i = 0; i < 15; ++i) {
            // Mapping Cloud Id --- Priceing API Id
            ResourceEntity resourceEntity = new ResourceEntity();
            //resourceEntity = resourceEntityRepository.save(resourceEntity);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -dataCreator.getRandom(1, 150000));
            // copy start date to give to the ResourceTypeMockCreator to ensure that the prices are generated from the starting date
            Calendar startDate = Calendar.getInstance();
            startDate.setTime(cal.getTime());

            ResourceType type = resourceTypeMockCreator.generateResourceType(startDate);

            int random = dataCreator.getRandom(1, 8);
            // 50 * 15 * 8
            // Create $random resourcelogs of the specified resourceEntity
            for (int j = 0; j < random; ++j) {

                ResourceLog resourceLog = new ResourceLog();
                resourceLog.setType(type);
                resourceLog.setResourceEntity(resourceEntity);


                resourceLog.setQuantity(generateResourceQuantity(resourceLog.getType().getType()));
                resourceLog.setEvent(eventMockCreator.createStartedEvent());

                cal.add(Calendar.MINUTE, dataCreator.getRandom(1, 40000));
                resourceLog.setCreated(cal.getTime());
                resourceLogs.add(resourceLog);

                // For every started event where there is another started event in creation should be a
                // stop event. The last stop event can be open.
                if (j + 1 < random || dataCreator.getBetween(true, false)) {
                    ResourceLog stoppedLog = new ResourceLog();
                    stoppedLog.setType(resourceLog.getType());
                    stoppedLog.setResourceEntity(resourceEntity);
                    stoppedLog.setEvent(eventMockCreator.createStoppedEvent());
                    stoppedLog.setQuantity(resourceLog.getQuantity());

                    cal.add(Calendar.MINUTE, dataCreator.getRandom(1, 20000));
                    stoppedLog.setCreated(cal.getTime());

                    resourceLogs.add(stoppedLog);
                }
            }
        }
        return resourceLogs;
    }
}
