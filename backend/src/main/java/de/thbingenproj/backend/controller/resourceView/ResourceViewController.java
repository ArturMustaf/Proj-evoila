package de.test.backend.controller.resourceView;

import de.test.backend.controller.resourceView.models.ResourceViewModel;
import de.test.backend.entities.prices.RuntimePair;
import de.test.backend.entities.repositories.ProjectRepository;
import de.test.backend.entities.repositories.RuntimePairRepository;
import de.test.backend.entities.ressources.ResourceEntity;
import de.test.backend.entities.ressources.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
/**
 * HTTP Controller to manage ResourceView http requests.
 */
public class ResourceViewController {
    private final ProjectRepository repository;

    ResourceViewController(ProjectRepository repository) {
        this.repository = repository;
    }

    @Autowired
    RuntimePairRepository runtimePairRepository;

    /**
     * Calculates the active running instances
     *
     * @return List of the ResourceInformations
     */

    @GetMapping("/resourceview")
    public List<ResourceViewModel> resourceViewTimeframe(@RequestParam(name = "project") Long projectId,
                                                         @RequestParam(name = "from", required = false) Long from,
                                                         @RequestParam(name = "to", required = false) Long to) {
        Date start;
        Date end;
        if (to == null) {
            end = Calendar.getInstance().getTime();
        } else {
            end = new Date(to);
        }
        if (from == null) {
            var calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.SECOND, 0);

            start = calendar.getTime();
        } else {
            start = new Date(from);
        }
        List<ResourceViewModel> result = new ArrayList<>();
        var project = repository.findById(projectId).orElse(null);
        if (project == null) {
            return result;
        }
        List<RuntimePair> pairList = runtimePairRepository.findRuntimePairsByEndDateAndStartDateAndProject(start, end, project);
        HashMap<ResourceType, ResourceViewModel> map = new HashMap<>();
        Set<ResourceEntity> machinesRun = new HashSet<>();
        for (RuntimePair pair : pairList) {
            ResourceType type = pair.started.getType();
            if (!map.containsKey(type)) {
                map.put(pair.started.getType(), new ResourceViewModel(type.getType(), 0, type.getUnitType(), 0));
            }
            ResourceViewModel model = map.get(type);
            if (!machinesRun.contains(pair.started.getResourceEntity())) {
                model.totalMachinesRun += 1;
                machinesRun.add(pair.started.getResourceEntity());
            }
            if (pair.stopped == null || pair.stopped.getCreated().after(end)) {
                model.quantity += 1;
            }
        }
        return new ArrayList<>(map.values());
    }
}
