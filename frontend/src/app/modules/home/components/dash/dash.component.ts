import {Component, OnInit} from '@angular/core';
import {NavService} from '../../../../core/services/nav/nav.service';
import {DataCommunicatorService} from '../../../../core/services/data_communicator/data-communicator.service';
import {ResourceModel} from '../../../../shared/models/resource-models/resource-model';
import {PricesModel} from '../../../../shared/models/prices-models/prices-model';

/**
 * Dashboard component
 */
@Component({
  selector: 'app-dash',
  templateUrl: './dash.component.html',
  styleUrls: ['./dash.component.scss']
})
export class DashComponent implements OnInit {
  resources: ResourceModel[];
  prices: PricesModel[];
  totalCostsInCent: number = 0;
  dateNow: Date = new Date();

  constructor(private navService: NavService, private communicator: DataCommunicatorService) {
    navService.isAdmin = false;
    //communicator.getOrganization("1").then((res) => console.log(res));
    communicator.getVDCsByOrgID("dcf38302-f549-405b-8a03-23f23f4962af").then((res) => console.log(res));
    this.requestAllResourcesOfCurrentMonth();
    this.requestAllPricesOfCurrentMonth();
    this.aggregateTotalCostOfAllPricesOfCurrentMonth();
  }

  requestAllResourcesOfCurrentMonth() : void {
    this.communicator.getResourcesByOrgIDInGivenTime("dcf38302-f549-405b-8a03-23f23f4962af",
                                                        this.getFirstDayOfCurrentMonthInMS(),
                                                        this.dateNow.getMilliseconds()).then(
                                                            resources => this.resources = resources);
  }

  requestAllPricesOfCurrentMonth() : void {
    this.resources.forEach( resource => {
      this.communicator.getPricesByResourceIDInGivenTime(resource.id, this.getFirstDayOfCurrentMonthInMS(), this.dateNow.getMilliseconds()).then(
        pricesResp => pricesResp.forEach(
          priceResp => this.prices.push(priceResp)
        )
      );
    });
  }

  getFirstDayOfCurrentMonthInMS() : number {
    return new Date(this.dateNow.getFullYear(), this.dateNow.getMonth(), 1).getMilliseconds();
  }

  aggregateTotalCostOfAllPricesOfCurrentMonth(): void {
    let from = this.getFirstDayOfCurrentMonthInMS();
    let until = this.dateNow.getMilliseconds();
    this.prices.forEach( price => {
      let timeOfCurrentMonthInMS = until - from;
      let timeOfPriceInGivenTimeInMS = timeOfCurrentMonthInMS;
      if (price.startDate < from) {
        timeOfPriceInGivenTimeInMS -= (from - price.startDate);
      }
      if (price.endDate > until) {
        timeOfPriceInGivenTimeInMS -= (price.endDate - until);
      }
      this.totalCostsInCent += (timeOfPriceInGivenTimeInMS / timeOfCurrentMonthInMS) * price.priceInCent;
    });
  }


  ngOnInit(): void {
  }
}
