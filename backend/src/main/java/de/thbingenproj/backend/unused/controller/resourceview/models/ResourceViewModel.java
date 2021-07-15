package de.thbingenproj.backend.unused.controller.resourceview.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

/**
 * Helper class to send ResourceInformations
 */
public class ResourceViewModel {
      private String resourceName;
      private long quantity;
      private String unitType;
      private int totalMachinesRun;
}
