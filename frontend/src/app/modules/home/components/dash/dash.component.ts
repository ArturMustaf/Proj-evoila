import {Component, OnInit} from '@angular/core';
import {NavService} from '../../../../core/services/nav/nav.service';
import {DataCommunicatorService} from '../../../../core/services/data_communicator/data-communicator.service';
import {ResourceModel} from '../../../../shared/models/resource-models/resource-model';
import {PricesModel} from '../../../../shared/models/prices-models/prices-model';
import {ResTypeModel} from '../../../../shared/models/resource-models/res-type-model';
import {KeycloakService} from 'keycloak-angular';
import {OrgUserModel} from '../../../../shared/models/org-user-models/org-user-model';
import {VdcModel} from '../../../../shared/models/vdc-models/vdc-model';

/**
 * Dashboard component
 */
@Component({
  selector: 'app-dash',
  templateUrl: './dash.component.html',
  styleUrls: ['./dash.component.scss']
})

/*
 * Prepares data that needs to be displayed by the dash-costs-diagram-component.
 */

export class DashComponent implements OnInit {
  allCurrentResources: ResourceModel[];
  allCurrentResourceTypes: ResTypeModel[] = [];
  allCurrentPrices: PricesModel[];
  costsDataForDiagram: { name: string, value: number }[];
  costsOfCurrentMonth_TEST_VALUES = [{'name': 'CPU', 'value': 2300}, {'name': 'Storage', 'value': 1200}, {'name': 'RAM', 'value': 900}];
  dateNowInMS: number = Date.now();
  dateFirstDayOfMonthInMS: number = new Date(new Date().getFullYear(), new Date().getMonth(), 1).getTime();

  orgUser: OrgUserModel = {
    emailAddress: '',
    fullName: '',
    href: '',
    id: '',
    isEnabled: '',
    isExternal: '',
    isGroupRole: '',
    isLocked: '',
    name: '',
    orgID: '',
    orgRoleID: '',
    telephone: '',
    userID: ''
  };
  vdcList: VdcModel[] = [];

  constructor(private navService: NavService, private communicator: DataCommunicatorService, private keycloakService: KeycloakService) {
  }

  ngOnInit() {
    this.getCurrentUserFromBackend();
    this.setupCosts();
  }

  getCurrentUserFromBackend() {
    let userID = this.keycloakService.getKeycloakInstance().subject;
    this.communicator.getCurrentOrgUserByOrgKeycloakID(userID)
      .subscribe(data => {; console.log("org-id:" + data.orgID);this.communicator.getVDCsByOrgID(data.orgID)
        .subscribe(res => this.vdcList = res)});

  }

  //org-id ist: dcf38302-f549-405b-8a03-23f23f4962af


  /*
   * First, all resources used by the logged in user, that will be part of the next monthly bill, will be requested from the server.
   * Next all different resource types in use will be requested
   * The aggregation of the prices is per resource type
   * All aggregated costs will be pushed into costsDataForDiagram which will be used by the pie chart to display all relevant values
   * Finally costsDataForDiagram will be sorted in descending order.
   */
  async setupCosts() {
    await this.requestAllCurrentResources();
    await this.requestAllUniqueCurrentResourceTypes();
    console.log(this.allCurrentResources);
    console.log("???"); console.log(this.allCurrentResourceTypes);
    /*
    this.requestAllCurrentPrices();
    this.aggregateCurrentTotalCostsPerResType();
    this.costsDataForDiagram.sort(function(costObjA, costObjB) {
      return costObjB.value - costObjA.value;
    });*/

  }

  /*
   * Request all Resources that are being used since the first day of the current month until now
   */
  //ToDO set orgID with keycloak
  async requestAllCurrentResources() {
    await this.communicator.getResourcesByOrgIDInGivenTime('dcf38302-f549-405b-8a03-23f23f4962af',
      this.dateFirstDayOfMonthInMS,
      this.dateNowInMS).toPromise().then(
      resources => {
        this.allCurrentResources = resources;
      });
  }

  /*
   * Request all different resource types that are being used, types that are not in use will not be displayed in the chart
   */
  async requestAllUniqueCurrentResourceTypes() {
    let allCurrentResourceTypeIDs = this.allCurrentResources.map(resource => resource.resourceTypeID);
    let allCurrentUniqueResourceTypeIDs = new Set(allCurrentResourceTypeIDs);
    allCurrentUniqueResourceTypeIDs.forEach(resourceTypeID => {
      this.communicator.getResType(resourceTypeID).toPromise().then(
        resTypeResp => this.allCurrentResourceTypes.push(resTypeResp)
      )
    });
  }

  /*
   * For each resource this method requests all prices that are associated with it and that are in the given timeframe.
   * All prices will be pushed together into one array.
   * Probably a better solution would be to push the prices into arrays per resource type
   */
  requestAllCurrentPrices() {
    this.allCurrentResources.forEach(resource => {
      this.communicator.getPricesByResourceIDInGivenTime(resource.id, this.dateFirstDayOfMonthInMS, this.dateNowInMS)
        .toPromise().then( pricesResp => pricesResp.forEach(
          priceResp => this.allCurrentPrices.push(priceResp)
        )
      );
    });
  }

  /*
   * The method iterates over all different resource types. Depending on the current resource type,
   * all available prices will be filtered, converted to the price in € and aggregated with a reduce function.
   */
  aggregateCurrentTotalCostsPerResType(): void {
    this.allCurrentResourceTypes.forEach(resType => {
      let allCurrentPricesOfType = this.allCurrentPrices.filter(price => price.resourceID == resType.id);
      let totalCostForResourcesOfType = allCurrentPricesOfType.reduce(function(previous, currentPrice) {
        return previous + this.calculatePriceForCurrentMonth(currentPrice);
      }, 0);
      this.costsDataForDiagram.push({'name': resType.typeOfResource, 'value': totalCostForResourcesOfType});
    });
  }

  /*
   *
   */
  //ToDo evtl überarbeiten "if(price.endDate == 0) { "?
  calculatePriceForCurrentMonth(price: PricesModel): number {
    let priceStartDateThisMonthInMS = price.startDate < this.dateFirstDayOfMonthInMS
      ? this.dateFirstDayOfMonthInMS
      : price.startDate;
    let priceEndDateThisMonthInMS;
    if(price.endDate == 0) {
      priceEndDateThisMonthInMS = this.dateNowInMS;
    }
    else {
      let date = new Date();
      let firstDayOfNextMonthInMS = new Date(date.getFullYear(), date.getMonth() + 1, 0).getTime();
      if (price.endDate >= firstDayOfNextMonthInMS) {
        priceEndDateThisMonthInMS = firstDayOfNextMonthInMS - 1;
      }
      else {
        priceEndDateThisMonthInMS = price.endDate;
      }
    }
    let priceDurationForCurrentMonthInMS = priceEndDateThisMonthInMS - priceStartDateThisMonthInMS;
    let timeOfPriceInHours = priceDurationForCurrentMonthInMS / (1000 * 60 * 60);
    let priceForCurrentMonthInEuro = (price.priceInCent * timeOfPriceInHours) / 100;
    return priceForCurrentMonthInEuro;
  }
}
