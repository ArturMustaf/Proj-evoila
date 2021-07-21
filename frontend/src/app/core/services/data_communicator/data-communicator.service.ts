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
    return this.http.get<SpaceModel[]>(environment.backend.address + "/organizations/" + id + "/spaces");
  }
}
