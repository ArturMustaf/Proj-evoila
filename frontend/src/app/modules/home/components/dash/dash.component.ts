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
export class DashComponent implements OnInit {
  allCurrentResources: ResourceModel[];
  allCurrentResourceTypes: ResTypeModel[];
  allCurrentPrices: PricesModel[];
  costsDataForDiagram: {name: string, value: number}[];
  costsOfCurrentMonth_TEST_VALUES = [{"name": "CPU", "value": 2300}, {"name": "Storage", "value": 1200}, {"name": "RAM", "value": 900}];
  dateNowInMS: number = Date.now();
  dateFirstDayOfMonthInMS: number = new Date(new Date().getFullYear(), new Date().getMonth(), 1).getTime() / 1;

  constructor(private navService: NavService, private communicator: DataCommunicatorService, private keycloakService: KeycloakService) {
    navService.isAdmin = false;
    communicator.getOrganization("1").then((res) => console.log(res));
    communicator.getVDCsByOrgID("dcf38302-f549-405b-8a03-23f23f4962af").then((res) => console.log(res));
  }

  async ngOnInit() {
    await this.setupCosts();
  }

  async setupCosts() {
    console.log("start");
    await this.requestAllCurrentResources();
    await this.requestAllUniqueCurrentResourceTypes()
    await this.requestAllCurrentPrices();
    this.aggregateCurrentTotalCostsPerResType();
    this.costsDataForDiagram.sort(function(costObjA, costObjB) {
      return costObjB.value - costObjA.value;
    });
    console.log("e");
  }

  async requestAllCurrentResources() {
    await this.communicator.getResourcesByOrgIDInGivenTime("dcf38302-f549-405b-8a03-23f23f4962af",
                                                        this.dateFirstDayOfMonthInMS,
                                                        this.dateNowInMS).then(
                                                            resources => {console.log("a");this.allCurrentResources = resources;});
    console.log("a");
  }

  async requestAllUniqueCurrentResourceTypes() {
    let allCurrentResourceTypeIDs = this.allCurrentResources.map( resource => resource.resourceTypeID );
    let allCurrentUniqueResourceTypeIDs = [... new Set(allCurrentResourceTypeIDs)].sort();
    await allCurrentUniqueResourceTypeIDs.forEach( resourceTypeID => {
      this.communicator.getResType(resourceTypeID).then( resTypeResp => this.allCurrentResourceTypes.push(resTypeResp));
    });
    console.log("b");
  }

  async requestAllCurrentPrices() {
    this.allCurrentResources.forEach(resource => {
      this.communicator.getPricesByResourceIDInGivenTime(resource.id, this.dateFirstDayOfMonthInMS, this.dateNowInMS).then(
        pricesResp => pricesResp.forEach(
          priceResp => this.allCurrentPrices.push(priceResp)
        )
      );
    });
    console.log("c");
  }

/*
  async test() {
    var userID = this.keycloakService.getKeycloakInstance().subject;
    var orgUser = await this.communicator.getCurrentOrgUserByUserID(userID);
    var vdcList = await this.communicator.getVDCsByOrgID(orgUser.orgID);
    vdcList.forEach(vdc => this.communicator.getVAppsByVdcID(vdc.id).then((res) => console.log(res)));
    vdcList.forEach(vdc => this.communicator.getVdcComputeCapacityByVdcID(vdc.id).then((res) => console.log(res)));
    vdcList.forEach(vdc => this.communicator.getVdcStorageCapacityByVdcID(vdc.id).then((res) => console.log(res)));
    console.log(orgUser);
    console.log(vdcList);
  }
*/

  aggregateCurrentTotalCostsPerResType(): void {
    let from = this.dateNowInMS;
    let until = this.dateFirstDayOfMonthInMS;
    this.allCurrentResourceTypes.forEach( resType => {
      let allCurrentPricesOfType = this.allCurrentPrices.filter( price => price.resourceID == resType.id );
      let totalCostForResourcesOfType = allCurrentPricesOfType.reduce( function(previous, current) {
        let timeOfPriceForCurrentMonthInHours = (until - from) / (1000 * 60 * 60);
        return previous + (current.priceInCent * timeOfPriceForCurrentMonthInHours);
      }, 0) ;
      this.costsDataForDiagram.push( {"name" : resType.typeOfResource, "value" : totalCostForResourcesOfType} );
    });
    console.log("d");
  }
}


