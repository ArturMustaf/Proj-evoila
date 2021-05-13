import {ResourceCommunicatorService} from '../services/resource_communicator/resource-communicator.service';
import {ResourceOverviewModel} from '../../shared/models/resource-models/resourceOverview-model';
import {Injectable} from '@angular/core';

@Injectable()
export class ResourceViewCommunicatorMock extends ResourceCommunicatorService {

  constructor() {
    super(null);
  }

  async loadResourceUsage(project: number): Promise<ResourceOverviewModel[]> {
    return [{resourceName: 'VM 2GB Ram', quantity: 2, unitType: 'Stück'}, {resourceName: 'Traffic', quantity: 2, unitType: 'GB'}];
  }
}
