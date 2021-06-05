package de.test.backend.controller.resourceView.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

/**
 * Helper class to send ResourceInformations
 */
public class ResourceViewModel {
      public String resourceName;
      public long quantity;
      public String unitType;
      public int totalMachinesRun;
}
