import { Component, OnInit } from '@angular/core';
import {VdcModel} from '../../../../shared/models/vdc-models/vdc-model';


@Component({
  selector: 'app-single-vdc',
  templateUrl: './single-vdc.component.html',
  styleUrls: ['./single-vdc.component.scss']
})
export class SingleVDCComponent implements OnInit {
  vdc: VdcModel;
  constructor() {
  }

  ngOnInit(): void {
  }

}
