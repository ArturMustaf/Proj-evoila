import { Injectable } from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {OrganizationModel} from '../../../shared/models/organization-models/organization-model';
import {VdcModel} from '../../../shared/models/vdc-models/vdc-model';
import {VdcComputeCapacity} from '../../../shared/models/vdc-models/vdc-compute-capacity';
import {VdcStorageProfile} from '../../../shared/models/vdc-models/vdc-storage-profile';
import {VappModel} from '../../../shared/models/vapp-models/vapp-model';
import {ResourceModel} from '../../../shared/models/resource-models/resource-model';
import {PricesModel} from '../../../shared/models/prices-models/prices-model';

@Injectable({
  providedIn: 'root'
})
export class DataCommunicatorService {

  constructor(private httpService: HttpClient) {

  }

  async getOrganization(id: string): Promise<OrganizationModel[]> {
    console.log("getOrg");
    return await this.httpService.get<OrganizationModel[]>(environment.backend.address + "admin/organizations").toPromise();
  }
  async getVDCsByOrgID(id: string): Promise<VdcModel[]> {
    return await this.httpService.get<VdcModel[]>(environment.backend.address + "/organizations/" + id + "/vdcs").toPromise();
  }
  async getVAppsByVdcID(id: string): Promise<VappModel[]> {
    return await this.httpService.get<VappModel[]>(environment.backend.address + "/vdcs/" + id +"/vapps").toPromise();
  }
  async getVdcComputeCapacityByVdcID(id: string): Promise<VdcComputeCapacity> {
    return await this.httpService.get<VdcComputeCapacity>(environment.backend.address + "/vdcs/" + id + "/vdc-compute-capacities").toPromise();
  }
  async getVdcStorageCapacityByVdcID(id: string): Promise<VdcStorageProfile> {
    return await this.httpService.get<VdcStorageProfile>(environment.backend.address + "/vdcs/" + id +"/vdc-storage-profiles").toPromise();
  }
  async getResourcesByOrgIDInGivenTime(id: string, from: number, until: number): Promise<ResourceModel[]> {
    return await this.httpService.get<ResourceModel[]>(
      environment.backend.address + "/organizations/" + id + "/resources/" + from + "/" + until).toPromise();
  }
  async getPricesByResourceIDInGivenTime(id: string, from: number, until: number): Promise<PricesModel[]> {
    return await this.httpService.get<PricesModel[]>(
      environment.backend.address + "/resources/" + id + "/prices/" + from + "/" + until).toPromise();
  }
}
