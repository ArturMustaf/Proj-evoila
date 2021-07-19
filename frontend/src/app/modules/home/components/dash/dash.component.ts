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
  userID: string;
  orgUser: OrgUserModel;
  vdc: VdcModel;
  constructor(private navService: NavService, private communicator: DataCommunicatorService, private keycloakService: KeycloakService) {
    navService.isAdmin = false;

    this.userID = keycloakService.getKeycloakInstance().subject;

    console.log("hello" + this.orgUser);
    communicator.getVDCsByOrgID(this.orgUser.orgID).then((res) => console.log(res));
  }

  ngOnInit(): void {
  }
}
