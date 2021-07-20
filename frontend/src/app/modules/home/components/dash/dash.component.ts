import {Component, OnInit} from '@angular/core';
import {NavService} from '../../../../core/services/nav/nav.service';
import {DataCommunicatorService} from '../../../../core/services/data_communicator/data-communicator.service';
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
  constructor(private navService: NavService, private communicator: DataCommunicatorService, private keycloakService: KeycloakService) {
    navService.isAdmin = false;
    this.test();
  }
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
  ngOnInit(): void {
  }
}
