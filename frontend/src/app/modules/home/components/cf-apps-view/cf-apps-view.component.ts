import {Component, Input, OnInit} from '@angular/core';
import {DataCommunicatorService} from '../../../../core/services/data_communicator/data-communicator.service';
import {SpaceModel} from '../../../../shared/models/cf-models/space-model';

@Component({
  selector: 'app-cf-apps-view',
  templateUrl: './cf-apps-view.component.html',
  styleUrls: ['./cf-apps-view.component.css']
})
export class CfAppsViewComponent implements OnInit {
  @Input() spaceList: SpaceModel[] = [];
  constructor() { }



  ngOnInit(): void {
    console.log(this.spaceList);
  }

}
