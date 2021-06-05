package de.thbingenproj.backend.controller.costs.models;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;
// TODO remove TypeInformation type or change to shortDesc.

/**
 * Data class for the CostsController
 * contains the requested:
 * - totalCost over the specified timeframe
 * - projectName of the requested project
 * - resourceTypes e.g {"VM 2GB 2CPU": @see TypeInformation}
 */
@Getter
@Setter
@Data
@RequiredArgsConstructor

public class CostsModel {

    private final double totalCosts;

    private final String projectName;
    private final Map<String, TypeInformation> resourceTypes;

    /**
     * type: ResourceType e.g "VM 2GB 2CPU"
     * price: Price for the resource
     */
    public static class TypeInformation {
        public String type;
        public double price = 0d;
    }


}
