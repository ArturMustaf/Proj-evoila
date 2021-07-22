import {Component, Input, OnInit} from '@angular/core';
import {DataCommunicatorService} from '../../../../core/services/data_communicator/data-communicator.service';
import {AppModel} from '../../../../shared/models/cf-models/app-model';

@Component({
  selector: 'app-cf-apps-view',
  templateUrl: './cf-apps-view.component.html',
  styleUrls: ['./cf-apps-view.component.css']
})
export class CfAppsViewComponent implements OnInit {
  @Input() spaceList: string[] = [];
  @Input() appList1: AppModel[] = [];
  @Input() appList2: AppModel[] = [];


  constructor(private communicator: DataCommunicatorService) { }

  ngOnInit(): void {

  }

}
