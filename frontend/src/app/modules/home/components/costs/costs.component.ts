import {Component, OnInit} from '@angular/core';
import {ClrDatagridStateInterface} from '@clr/angular';
import {CostsCommunicatorService, CostsResponse} from '../../../../core/services/costs-communicator/costs-communicator.service';
import {NavService} from '../../../../core/services/nav/nav.service';
import {ProjectDescription} from '../../../../core/navigation/project-dropdown/project-dropdown.component';

@Component({
  selector: 'app-costs',
  templateUrl: './costs.component.html',
  styleUrls: ['./costs.component.scss']
})
/**
 * Component for calculation of costs
 */
export class CostsComponent implements OnInit {

  loading: boolean;
  costResponses: CostsResponse[] = [];
  // tslint:disable-next-line:variable-name
  _start = '';
  startDate: Date;
  endDate: Date;
  today: Date;
  dateOfToday: string;
  // tslint:disable-next-line:variable-name
  _end = '';
  validSpan = true;

  projectId = '';
  // tslint:disable-next-line:no-inferrable-types
  totalCost: number = 100000000;
  projects: ProjectDescription[];
  ressources: Map<string, number>;

  // added for date validation


  constructor(private communicator: CostsCommunicatorService, private navService: NavService) {
    // set max to todays date
    this.today = new Date();
    this.dateOfToday = this.today.getFullYear() + '-' + (this.today.getMonth() + 1) + '-' + this.today.getDate();
    this.projects = navService.projects;
    navService.OnProjectChanged.subscribe((x) => {
      this.projects = x;
      this.costResponses = [];
      this.costLoading();
    });
    this.costLoading();
  }

  refresh(state: ClrDatagridStateInterface): void {
  }

  get start(): string {
    return this._start;
  }

  set start(s: string) {
    this._start = s;
    this.costLoading();
  }

  get end(): string {
    return this._end;
  }

  set end(e: string) {
    this._end = e;
    this.costLoading();
  }

  async ngOnInit(): Promise<void> {
    await this.costLoading();
  }

  /**
   * Load costs out of the backend
   */
  async costLoading(): Promise<void> {
    this.loading = true;
    this.costResponses = [];
    // if date invalid, do not send to backend
    if (this.start !== '' && this.end !== '' && (this.start > this.end)) {
      this.validSpan = false;
    } else {
      this.validSpan = true;
    }
    for (const project of this.projects) {
      this.costResponses = await this.communicator.loadCosts(this.start, this.end, project.id);
    }
    this.totalCost = 0;
    const map = new Map<string, number>();
    this.costResponses.forEach(project => {

      for (const resource of Object.keys(project.resourceTypes)) {
        if (!map.has(resource)) {
          map.set(resource, 0);
        }
        map.set(resource, map.get(resource) + project.resourceTypes[resource].price);
        this.totalCost = this.totalCost + project.resourceTypes[resource].price;
      }
    });
    this.ressources = map;
    this.loading = false;
  }

}
