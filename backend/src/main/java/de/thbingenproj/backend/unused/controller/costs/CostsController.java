package de.thbingenproj.backend.unused.controller.costs;

import de.thbingenproj.backend.unused.controller.costs.models.CostsModel;
import de.thbingenproj.backend.unused.controller.costs.models.Pair;
import de.thbingenproj.backend.unused.controller.costs.models.PriceIterator;
import de.thbingenproj.backend.unused.entities.prices.Price;
import de.thbingenproj.backend.unused.entities.prices.RuntimePair;
import de.thbingenproj.backend.unused.entities.prices.price.PriceType;
import de.thbingenproj.backend.unused.entities.repositories.*;
import de.thbingenproj.backend.unused.entities.ressources.ResourceEntity;
import de.thbingenproj.backend.unused.entities.ressources.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Restcontroller which calculates the price of a requested project id
 */


@RestController
public class CostsController {
    private final ProjectRepository repository;

    CostsController(ProjectRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public ResourceLogRepository resourceLogRepository;
    @Autowired
    public EventRepository eventRepository;
    @Autowired
    public ResourceEntityRepository resourceEntityRepository;
    @Autowired
    public ApplicationEventPublisher eventPublisher;
    @Autowired
    RuntimePairRepository runtimePairRepository;

    /**
     * @param id      id of the requested project
     * @param startTS start timestamp of the wanted timeframe or null
     * @param endTS   end timestamp of the wanted timeframe or null
     * @return List with CostModel objects
     */

    @GetMapping("/costs")
    @Transactional
    public List<CostsModel> costs(@RequestParam() long id, @RequestParam(required = false) Long startTS,
                                  @RequestParam(required = false) Long endTS) {

        Date start;
        Date end;

        if (endTS == null) {
            end = Calendar.getInstance().getTime();
        } else {
            end = new Date(endTS);
        }
        if (startTS == null) {
            var calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.SECOND, 0);

            start = calendar.getTime();
        } else {
            start = new Date(startTS);
        }


        List<CostsModel> result = new ArrayList<>();
        result.add(priceForProject(id, start, end));

        return result;
    }

    /**
     * @param id   wanted project id
     * @param from timestamp from
     * @param to   timestamp to
     * @return CostModel over the timeframe
     */
    @Transactional
    public CostsModel priceForProject(long id, Date from, Date to) {
        var project = repository.findById(id).orElse(null);
        if (project == null) {
            return null;
        }
        var totalPrice = 0L;
        List<RuntimePair> runtimes = runtimePairRepository.findRuntimePairsByEndDateAndStartDateAndProject(from, to, project);

        HashMap<ResourceEntity, Pair<ResourceType, Long>> entityPriceMap = new HashMap<>();

        for (RuntimePair pair : runtimes) {
            // grabbing all infos which we want to calculate for entity
            ResourceType type = pair.started.getType();
            ResourceEntity runEntity = pair.entity;
            long price = 0;
            // create iterator over prices
            var priceIterator = new PriceIterator(type);
            Date validTimeStart = pair.started.getCreated().before(from) ? from : pair.started.getCreated();

            Date validTimeEnd;
            if (pair.stopped == null) {
                validTimeEnd = to;
            } else if (pair.stopped.getCreated().after(to)) {
                validTimeEnd = to;
            } else {
                validTimeEnd = pair.stopped.getCreated();
            }

            while (priceIterator.hasNext()) {
                var priceObject = priceIterator.next();
                Pair<Long, Long> timeinRange = priceObject.getTimeInTimeRange(validTimeStart, validTimeEnd);
                long timerun = timeinRange.getElm1();
                price += calculatePrice(priceObject, timerun);

            }
            if (!entityPriceMap.containsKey(runEntity)) {
                entityPriceMap.put(runEntity, new Pair<>(type, 0L));
            }
            entityPriceMap.get(runEntity).setElm2(entityPriceMap.get(runEntity).getElm2() + price);
            totalPrice += price;
        }
        HashMap<String, CostsModel.TypeInformation> resourceTypes = new HashMap<>();
        for (Pair<ResourceType, Long> result : entityPriceMap.values()) {
            if (!resourceTypes.containsKey(result.getElm1().getType())) {
                resourceTypes.put(result.getElm1().getType(), new CostsModel.TypeInformation());
            }
            var typeInformation = resourceTypes.get(result.getElm1().getType());
            typeInformation.setPrice(typeInformation.getPrice() + (double) result.getElm2() / 100);
            typeInformation.setType(result.getElm1().getType());

        }

        return new CostsModel(totalPrice, project.getName(), resourceTypes);

    }


    /**
     * @param price price object in timeframe
     * @param time  timeamount in millis which the ressource of the price was running
     * @return
     */
    private long calculatePrice(Price price, long time) {
        // TODO support more time intervals

        var type = price.getPriceType();
        if (type == PriceType.HOURLY_PRICE) {
            long hours = time / (60 * 60 * 1000);
            return Math.abs(price.getPriceInCt() * hours);

        }
        if (type == PriceType.MINUTE_PRICE) {
            long minutes = time / (60 * 1000);
            return Math.abs(price.getPriceInCt() * minutes);
        }


        return 0L;
    }

}
