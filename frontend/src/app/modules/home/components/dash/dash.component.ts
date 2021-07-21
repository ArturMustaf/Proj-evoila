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
  allCurrentPricesByType: { 'resourceTypeID': number, 'prices': PricesModel[] }[] = [];
  costsDataForDiagram: { name: string, value: number }[] = [];
  //costsOfCurrentMonth_TEST_VALUES = [{'name': 'CPU', 'value': 2300}, {'name': 'Storage', 'value': 1200}, {'name': 'RAM', 'value': 900}];
  dateNowInMS: number = Date.now();
  dateFirstDayOfMonthInMS: number = new Date(new Date().getFullYear(), new Date().getMonth(), 1).getTime();
  isDataReady: boolean = false;
  orgID: string;

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

  async ngOnInit() {
    this.getCurrentUserFromBackend();
    await this.setupCosts();
  }

  getCurrentUserFromBackend() {
    let userID = this.keycloakService.getKeycloakInstance().subject;
    this.communicator.getCurrentOrgUserByOrgKeycloakID(userID)
      .subscribe(data => {
        this.orgID = data.orgID;
        this.communicator.getVDCsByOrgID(data.orgID)
          .subscribe(res => this.vdcList = res)
      });
  }

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
    await this.requestAllCurrentPrices();
    this.aggregateCurrentTotalCostsPerResType();


    this.costsDataForDiagram.sort(function(costObjA, costObjB) {
      return costObjB.value - costObjA.value;
    });
    this.isDataReady = true;
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
      );
    });
  }

  /*
   * For each resource this method requests all prices that are associated with it and only those that are in the given timeframe.
   * All prices will be pushed together into an array and linked to the corresponding resource type,
   * both together as an object are an entry in "allCurrentPricesByType"
   */
  async requestAllCurrentPrices() {
    for (const resource of this.allCurrentResources) {
      await this.communicator.getPricesByResourceIDInGivenTime(
        resource.id, this.dateFirstDayOfMonthInMS, this.dateNowInMS).toPromise().then(
        pricesResp => {
          if (this.allCurrentPricesByType.filter(entry => entry.resourceTypeID === resource.resourceTypeID).length == 0) {
            this.allCurrentPricesByType.push({'resourceTypeID': resource.resourceTypeID, prices: []});
          }
          this.allCurrentPricesByType.find(function(entry) {
            if (entry.resourceTypeID == resource.resourceTypeID) {
              entry.prices.push(...pricesResp);
            }
          });
        }
      );
    }
  }

  /*
   * The method iterates over all different resource types and the associated array of price objects.
   * Depending on the current resource type, all available prices will be added up to the total cost
   * of the current resource type and pushed into the costsDataForDiagram object,
   * which will be used by the pie chart diagram to display the data
   */
  aggregateCurrentTotalCostsPerResType(): void {
    for (const pricesOfType of this.allCurrentPricesByType) {
      let totalCostForResourcesOfType = 0;
      for (const currentPrice of pricesOfType.prices) {
        totalCostForResourcesOfType += this.calculatePriceForCurrentMonth(currentPrice);
      }
      let resourceTypeName = this.allCurrentResourceTypes.find(
        resType => {
          if (resType.id == pricesOfType.resourceTypeID) {
            return true;
          }
        }).typeOfResource;
      this.costsDataForDiagram.push({'name': resourceTypeName, 'value': totalCostForResourcesOfType});
    }
  }

  /*
   * Method for calculating the valid price of a price object for the current month.
   * for the case that enddate is 0, which means that the price currently just doesn't have an enddate set,
   * the method treats the price in this case as if the enddate is set to the moment when this method gets called.
   * If the value of the startDate is set to a day before the first day of the current month (strictly speaking the moment in milliseconds
   * and not the day), the method takes the first day of the month as the startdate.
   * Otherwise the startdate has to be one of the remaining days of the current month.
   * return value of this method is the price in €
   */
  calculatePriceForCurrentMonth(price: PricesModel): number {
    let priceStartDateThisMonthInMS = price.startDate < this.dateFirstDayOfMonthInMS
      ? this.dateFirstDayOfMonthInMS
      : price.startDate;
    let priceEndDateThisMonthInMS;
    if (price.endDate == 0) {
      priceEndDateThisMonthInMS = this.dateNowInMS;
    } else {
      let date = new Date();
      let firstDayOfNextMonthInMS = new Date(date.getFullYear(), date.getMonth() + 1, 0).getTime();
      if (price.endDate >= firstDayOfNextMonthInMS) {
        priceEndDateThisMonthInMS = firstDayOfNextMonthInMS - 1;
      } else {
        priceEndDateThisMonthInMS = price.endDate;
      }
    }
    let priceDurationForCurrentMonthInMS = priceEndDateThisMonthInMS - priceStartDateThisMonthInMS;
    let timeOfPriceInHours = priceDurationForCurrentMonthInMS / (1000 * 60 * 60);
    return (price.priceInCent * timeOfPriceInHours) / 100;
  }
}
