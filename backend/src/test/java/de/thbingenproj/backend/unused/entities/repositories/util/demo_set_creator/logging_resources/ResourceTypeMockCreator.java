package de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.logging_resources;

import de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.user_information.PriceMockCreator;
import de.thbingenproj.backend.unused.entities.repositories.ResourceTypeRepository;
import de.thbingenproj.backend.unused.entities.repositories.util.demo_set_creator.dataset.DataCreator;
import de.thbingenproj.backend.unused.entities.ressources.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
/**
 * Mock creator for ResourceTypes
 */
public class ResourceTypeMockCreator {
    @Autowired
    DataCreator dataCreator;
    @Autowired
    PriceMockCreator priceCreator;
    @Autowired
    ResourceTypeRepository resourceTypeRepository;

    private String generateResourceDescriptionType() {
        String generatedResource = dataCreator.getBetween("VM", "Traffic", "Static IP");
        //TODO Add Other Types than VM
        if (generatedResource.equals("VM")) {
            return generateServer("VM ");
        } else {
            return generatedResource;
        }
        //return generateServer("VM ");
    }

    private String generateResourceUnitType(String resourceDescription) {
        if (resourceDescription.startsWith("VM"))
            return "pc.";
        else if (resourceDescription.startsWith("Static IP"))
            return "pc.";
        else if (resourceDescription.startsWith("Traffic"))
            return "Gb";
        else if (resourceDescription.startsWith("Storage"))
            return "GiB";
        return "unknown resource";

//        switch (resourceDescription){
//            case "VM":
//            case "Static IP":
//                return "pc.";
//            case "Traffic":
//                return "Gb";
//            case "Storage":
//                return "GiB";
//            default:
//                return "";
//        }
    }


    private String generateServer(String base) {
        return base + dataCreator.getBetween("2 Cores 4GB RAM", "4 Cores 4GB RAM", "4 Cores 8GB RAM",
                "4 Cores 16GB RAM", "8 Cores 16GB RAM", "4 Cores 32GB RAM");
    }

    public ResourceType generateResourceType(Calendar calendar) {
        // Description and unit type
        String generatedDescription = generateResourceDescriptionType();
        String generatedUnitType = generateResourceUnitType(generatedDescription);

        // recursive spawning of a resourcetype with price
        ResourceType resourceType = generateResourceType(generatedDescription, generatedUnitType, calendar);

        //resourceTypeRepository.save(resourceType);
        return resourceType;
    }

    private ResourceType generateResourceType(String description, String generatedUnitType, Calendar calendar) {
        ResourceType resourceType = new ResourceType();
        resourceType.setShortDescription(description);
        resourceType.setFullDescription(description);
        resourceType.setType(description);
        resourceType.setPrice(priceCreator.createPrices(calendar));
        resourceType.setUnitType(generatedUnitType);

        if (dataCreator.getBetween(true, false)) {
            ResourceType next = generateResourceType(description, generatedUnitType, calendar);
            resourceTypeRepository.save(next);
            resourceType.setNextResource(next);
            //next.setPreviousResource(resourceType);
        } else {
            resourceType.getPrice().get(resourceType.getPrice().size() - 1).setExpireDate(null);
        }


        resourceTypeRepository.save(resourceType);

        return resourceType;
    }
}
