import {Injectable } from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {VdcModel} from '../../../shared/models/vdc-models/vdc-model';
import {VdcComputeCapacity} from '../../../shared/models/vdc-models/vdc-compute-capacity';
import {VdcStorageProfile} from '../../../shared/models/vdc-models/vdc-storage-profile';
import {VappModel} from '../../../shared/models/vapp-models/vapp-model';
import {OrgUserModel} from '../../../shared/models/org-user-models/org-user-model';
import {AppModel} from '../../../shared/models/cf-models/app-model';
import {SpaceModel} from '../../../shared/models/cf-models/space-model';
import {ResourceModel} from '../../../shared/models/resource-models/resource-model';
import {PricesModel} from '../../../shared/models/prices-models/prices-model';
import {ResTypeModel} from '../../../shared/models/resource-models/res-type-model';

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

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  constructor(private http: HttpClient) {

  }
  getCurrentOrgUserByOrgKeycloakID(id: string): Observable<OrgUserModel> {
    return this.http.get<OrgUserModel>(environment.backend.address + "/org-users/keycloak/"+ id);
  }
  getVDCsByOrgID(id: string): Observable<VdcModel[]> {
    return this.http.get<VdcModel[]>(environment.backend.address + "/organizations/" + id + "/vdcs");
  }
  getVAppsByVdcID(id: string): Observable<VappModel[]>{
    return this.http.get<VappModel[]>(environment.backend.address + "/vdcs/" + id +"/vapps");
  }
  getLatestVdcComputeCapacityByVdcID(id: string): Observable<VdcComputeCapacity> {
    return this.http.get<VdcComputeCapacity>(environment.backend.address + "/vdcs/" + id + "/vdc-compute-capacities/latest");
  }
  getVdcStorageCapacityByVdcID(id: string): Observable<VdcStorageProfile> {
    return this.http.get<VdcStorageProfile>(environment.backend.address + "/vdcs/" + id +"/vdc-storage-profiles/latest");
  }
  getSpacesByOrgName(id: string): Observable<SpaceModel[]> {
    return this.http.get<SpaceModel[]>(environment.backend.address + "/organizations/" + id + "/apps/spaces");
  }

  getLatestAppsByOrgIDAndSpaceName(orgID: string, spaceName: string): Observable<AppModel[]> {
    return this.http.get<AppModel[]>(environment.backend.address + "/organizations/" + orgID +"/" +spaceName + "/latest-apps");
  }
  getResourcesByOrgIDInGivenTime(id: string, from: number, until: number): Observable<ResourceModel[]> {
    return this.http.get<ResourceModel[]>(environment.backend.address + "/organizations/" + id + "/resources/" + from + "/" + until);
  }
  getPricesByResourceIDInGivenTime(id: number, from: number, until: number): Observable<PricesModel[]> {
    return this.http.get<PricesModel[]>(environment.backend.address + "/resources/" + id + "/prices/" + from + "/" + until);
  }
  getResType(id: number): Observable<ResTypeModel> {
    return this.http.get<ResTypeModel>(environment.backend.address + "/resource-types/" + id);
  }
}
