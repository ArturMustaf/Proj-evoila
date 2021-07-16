import {Component, OnInit} from '@angular/core';
import {NavService} from '../../../../core/services/nav/nav.service';
import {DataCommunicatorService} from '../../../../core/services/data_communicator/data-communicator.service';

/**
 * Dashboard component
 */
@Component({
  selector: 'app-dash',
  templateUrl: './dash.component.html',
  styleUrls: ['./dash.component.scss']
})
export class DashComponent implements OnInit {

  constructor(private navService: NavService, communicator: DataCommunicatorService) {
    navService.isAdmin = false;
    communicator.getOrganization("1").then((res) => console.log(res));
    communicator.getVDCsByOrgID("1").then((res) => console.log(res));
  }


  ngOnInit(): void {
  }
}
