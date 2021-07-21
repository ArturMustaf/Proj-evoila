import {Component, Input, OnInit} from '@angular/core';
import {DataCommunicatorService} from '../../../../core/services/data_communicator/data-communicator.service';
import {SpaceModel} from '../../../../shared/models/cf-models/space-model';
import {AppModel} from '../../../../shared/models/cf-models/app-model';

@Component({
  selector: 'app-cf-apps-view',
  templateUrl: './cf-apps-view.component.html',
  styleUrls: ['./cf-apps-view.component.css']
})
export class CfAppsViewComponent implements OnInit {
  @Input() spaceList: SpaceModel[] = [];
  constructor(private communicator: DataCommunicatorService) { }

  getApps(){
    this.communicator.getLatestAppsByOrgIDAndSpaceID("AAAA", "demo")
      .subscribe(res => console.log(res + "test"));
  }


  ngOnInit(): void {
    this.getApps();
  }

}
