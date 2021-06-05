package de.test.backend.controller.ResourceView.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

/**
 * Helper class to send ResourceInformations
 */
public class ResourceViewModel {
      public String ResourceName;
      public long Quantity;
      public String UnitType;
      public int totalMachinesRun;
}
