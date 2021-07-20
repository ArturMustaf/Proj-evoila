import {Component} from '@angular/core';
import {NavService} from '../../../../core/services/nav/nav.service';
import {DataCommunicatorService} from '../../../../core/services/data_communicator/data-communicator.service';
import {ResourceModel} from '../../../../shared/models/resource-models/resource-model';
import {PricesModel} from '../../../../shared/models/prices-models/prices-model';
import {ResType} from '../../../../shared/models/resource-models/res-type-model';

/**
 * Dashboard component
 */
@Component({
  selector: 'app-dash',
  templateUrl: './dash.component.html',
  styleUrls: ['./dash.component.scss']
})
export class DashComponent {
  allCurrentResources: ResourceModel[];
  allCurrentResourceTypes: ResType[];
  allCurrentPrices: PricesModel[];
  costsOfCurrentMonth: any[];
  costsOfCurrentMonth_TEST_VALUES = [{"name": "CPU", "value": 2300}, {"name": "Storage", "value": 1200}, {"name": "RAM", "value": 900}];
  dateNow: Date = new Date();

  constructor(private navService: NavService, private communicator: DataCommunicatorService) {
    navService.isAdmin = false;
    communicator.getOrganization("1").then((res) => console.log(res));
    communicator.getVDCsByOrgID("dcf38302-f549-405b-8a03-23f23f4962af").then((res) => console.log(res));
    //this.setupCosts();
  }

  setupCosts() {
    this.requestAllResourcesOfCurrentMonth();
    this.requestAllUniqueResourceTypesOfCurrentMonth();
    this.requestAllPricesOfCurrentMonth();
    this.aggregateTotalCostsOfAllPricesForCurrentMonth();
  }

  requestAllResourcesOfCurrentMonth() : void {
    this.communicator.getResourcesByOrgIDInGivenTime("dcf38302-f549-405b-8a03-23f23f4962af",
                                                        this.getFirstDayOfCurrentMonthInMS(),
                                                        this.dateNow.getMilliseconds()).then(
                                                            resources => this.allCurrentResources = resources);
  }

  requestAllUniqueResourceTypesOfCurrentMonth() : void {
    let allCurrentResourceTypeIDs = this.allCurrentResources.map( resource => resource.resourceTypeID );
    let allCurrentUniqueResourceTypeIDs = [... new Set(allCurrentResourceTypeIDs)].sort();
    allCurrentUniqueResourceTypeIDs.forEach( resourceTypeID => {
      this.communicator.getResType(resourceTypeID).then( resTypeResp => this.allCurrentResourceTypes.push(resTypeResp));
    });
  }

  requestAllPricesOfCurrentMonth() : void {
    this.allCurrentResources.forEach( resource => {
      this.communicator.getPricesByResourceIDInGivenTime(resource.id, this.getFirstDayOfCurrentMonthInMS(), this.dateNow.getMilliseconds()).then(
        pricesResp => pricesResp.forEach(
          priceResp => this.allCurrentPrices.push(priceResp)
        )
      );
    });
  }

  getFirstDayOfCurrentMonthInMS() : number {
    return new Date(this.dateNow.getFullYear(), this.dateNow.getMonth(), 1).getMilliseconds();
  }

  aggregateTotalCostsOfAllPricesForCurrentMonth(): void {
    let from = this.getFirstDayOfCurrentMonthInMS();
    let until = this.dateNow.getMilliseconds();
    let totalCostsInCent = 0;
    this.allCurrentPrices.forEach( price => {
      let timeOfCurrentMonthInMS = until - from;
      let timeOfPriceOfCurrentMonthInMS = timeOfCurrentMonthInMS;
      if (price.startDate < from) {
        timeOfPriceOfCurrentMonthInMS -= (from - price.startDate);
      }
      if (price.endDate > until) {
        timeOfPriceOfCurrentMonthInMS -= (price.endDate - until);
      }
      totalCostsInCent += (timeOfPriceOfCurrentMonthInMS / timeOfCurrentMonthInMS) * price.priceInCent;
    });
    this.costsOfCurrentMonth.push({"name": "current month", "value": totalCostsInCent});
  }
}
