import {Component, Input, OnInit} from '@angular/core';
import {VdcModel} from '../../../../shared/models/vdc-models/vdc-model';
import {DataCommunicatorService} from '../../../../core/services/data_communicator/data-communicator.service';
import {VappModel} from '../../../../shared/models/vapp-models/vapp-model';
import {VdcStorageProfile} from '../../../../shared/models/vdc-models/vdc-storage-profile';
import {VdcComputeCapacity} from '../../../../shared/models/vdc-models/vdc-compute-capacity';


@Component({
  selector: 'app-single-vdc',
  templateUrl: './single-vdc.component.html',
  styleUrls: ['./single-vdc.component.scss']
})
export class SingleVDCComponent implements OnInit {
  @Input() vdc: VdcModel;
  vApps: VappModel[] = [];
  storageProfile: VdcStorageProfile ={
    date: 0,
    enabled: '',
    href: '',
    id: '',
    iopsAllocated: '',
    maxLimit: '',
    name: '',
    storageUsedMB: '',
    units: '',
    vdcID: ''
  }
  computeCapacity: VdcComputeCapacity = {
    cpuAllocated: 0,
    cpuLimit: 0,
    cpuReserved: 0,
    cpuUnits: '',
    cpuUsed: 0,
    date: 0,
    id: 0,
    memoryAllocated: 0,
    memoryLimit: 0,
    memoryReserved: 0,
    memoryUnits: '',
    memoryUsed: 0,
    vdcID: ''
  }
  constructor(private communicator: DataCommunicatorService) {
  }
  getStorageProfile(){
    this.communicator.getVdcStorageCapacityByVdcID(this.vdc.id).subscribe(res => this.storageProfile = res);
  }
  getComputeCapacity(){
    this.communicator.getLatestVdcComputeCapacityByVdcID(this.vdc.id).subscribe(res => this.computeCapacity = res);
  }
  getVApps(){
    this.communicator.getVAppsByVdcID(this.vdc.id).subscribe(res => this.vApps = res);
  }
  ngOnInit(): void {
    this.getVApps();
    this.getStorageProfile();
    this.getComputeCapacity();
  }

}
