import { Injectable } from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {OrganizationModel} from '../../../shared/models/organization-models/organization-model';
import {VdcModel} from '../../../shared/models/vdc-models/vdc-model';
import {VdcComputeCapacity} from '../../../shared/models/vdc-models/vdc-compute-capacity';
import {VdcStorageProfile} from '../../../shared/models/vdc-models/vdc-storage-profile';
import {VappModel} from '../../../shared/models/vapp-models/vapp-model';
import {OrgUserModel} from '../../../shared/models/org-user-models/org-user-model';



@Injectable({
  providedIn: 'root'
})
/**
 * Class to get data that the current user is allowed to see
 * Steps:
 * User logs in
 * Userdata gets called from keycloak
 * With the userdata from keycloak the user is pulled from backend
 * User from backend has orgID which leads to all vdcs the org owns<
 * the vdcid leads to vapps,storage and compute capacity
 */
export class DataCommunicatorService {

  constructor(private httpService: HttpClient) {

  }

  async getCurrentOrgUserByUserID(id: string): Promise<OrgUserModel>{
    return this.httpService.get<OrgUserModel>(environment.backend.address + "/org-users/keycloak/"+ id ).toPromise();
  }
  async getOrganization(id: string): Promise<OrganizationModel[]> {
    return this.httpService.get<OrganizationModel[]>(environment.backend.address + "admin/organizations").toPromise();
  }
  async getVDCsByOrgID(id: string): Promise<VdcModel[]> {
    return this.httpService.get<VdcModel[]>(environment.backend.address + "/organizations/" + id + "/vdcs").toPromise();
  }
  async getVAppsByVdcID(id: string): Promise<VappModel[]> {
    return this.httpService.get<VappModel[]>(environment.backend.address + "/vdcs/" + id +"/vapps").toPromise();
  }
  async getVdcComputeCapacityByVdcID(id: string): Promise<VdcComputeCapacity> {
    return this.httpService.get<VdcComputeCapacity>(environment.backend.address + "/vdcs/" + id + "/vdc-compute-capacities").toPromise();
  }
  async getVdcStorageCapacityByVdcID(id: string): Promise<VdcStorageProfile> {
    return this.httpService.get<VdcStorageProfile>(environment.backend.address + "/vdcs/" + id +"/vdc-storage-profiles").toPromise();
  }
}
