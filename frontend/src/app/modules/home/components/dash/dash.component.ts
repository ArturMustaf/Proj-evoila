import {Component, OnInit} from '@angular/core';
import {NavService} from '../../../../core/services/nav/nav.service';
import {DataCommunicatorService} from '../../../../core/services/data_communicator/data-communicator.service';
import {KeycloakService} from 'keycloak-angular';
import {OrgUserModel} from '../../../../shared/models/org-user-models/org-user-model';
import {VdcModel} from '../../../../shared/models/vdc-models/vdc-model';
import {VdcComputeCapacity} from '../../../../shared/models/vdc-models/vdc-compute-capacity';
import {VdcStorageProfile} from '../../../../shared/models/vdc-models/vdc-storage-profile';
import {Observable} from 'rxjs';
import {SpaceModel} from '../../../../shared/models/cf-models/space-model';
import {AppModel} from '../../../../shared/models/cf-models/app-model';

/**
 * Dashboard component
 */
@Component({
  selector: 'app-dash',
  templateUrl: './dash.component.html',
  styleUrls: ['./dash.component.scss']
})

export class DashComponent implements OnInit {
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
  spaceList: SpaceModel[] = [];
  appList1: AppModel[] = [];
  appList2: AppModel[] = [];

  constructor(private navService: NavService, private communicator: DataCommunicatorService, private keycloakService: KeycloakService) {
  }

  getCurrentUserFromBackend(){
    let userID = this.keycloakService.getKeycloakInstance().subject;
    this.communicator.getCurrentOrgUserByOrgKeycloakID(userID)
      .subscribe(data => this.communicator.getVDCsByOrgID(data.orgID).subscribe(res => this.vdcList = res));
  }
  getSpaceList(){
    this.communicator.getSpacesByOrgName("AAAA").subscribe(res => this.spaceList = res);
  }

  ngOnInit(): void {
    this.getCurrentUserFromBackend();
    this.getSpaceList();
  }
}
