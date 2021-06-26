import {Component, Input, OnInit} from '@angular/core';
import {ClrDatagridStateInterface} from '@clr/angular';
import {ResourceOverviewModel} from '../../../../shared/models/resource-models/resourceOverview-model';
import {ResourceCommunicatorService} from '../../../../core/services/resource_communicator/resource-communicator.service';
import {NavService} from '../../../../core/services/nav/nav.service';
import {ProjectDescription} from '../../../../core/header-navigation/project-dropdown/project-dropdown.component';

@Component({
  selector: 'app-resource-view',
  templateUrl: './resource-view.component.html',
  styleUrls: ['./resource-view.component.scss']
})
/**
 * ResourceView component. This component shows the ressources the active user uses for the project
 */
export class ResourceViewComponent implements OnInit {
  loading: boolean;
  projectId = 1;
  projects: ProjectDescription[];
  resources: ResourceOverviewModel[];
  startDate: Date;
  endDate: Date;
  today: Date;
  dateOfToday: string;
  validSpan = true;

  constructor(private communicator: ResourceCommunicatorService, private navService: NavService) {
    this.projects = navService.projects;
    navService.OnProjectChanged.subscribe(x => {
      this.projects = x;
      this.loadResources();
    });
    this.loadResources();

  }

  // eslint-disable-next-line @typescript-eslint/naming-convention,no-underscore-dangle,id-blacklist,id-match
  _start = '';


  @Input()
  public name = 'Genutzte Ressourcen im gewählten Zeitraum';
  @Input()
  public onDash = false;

  get start(): string {
    return this._start;
  }

  set start(s: string) {
    this._start = s;
    this.loadResources();
  }

  // eslint-disable-next-line @typescript-eslint/naming-convention, no-underscore-dangle, id-blacklist, id-match
  _end = '';

  get end(): string {
    return this._end;
  }

  set end(e: string) {
    this._end = e;
    this.loadResources();
  }

  ngOnInit(): void {
    this.loadResources();
  }

  refresh(state: ClrDatagridStateInterface): void {
  }

  async loadResources(): Promise<void> {
    if (this.start !== '' && this.end !== '' && (this.start > this.end)) {
      this.validSpan = false;
    } else {
      this.validSpan = true;
    }
    this.loading = true;
    const tmpResourceList: ResourceOverviewModel[] = [];
    for (const x of this.projects) {
      if (this.onDash) {
        tmpResourceList.push(...(await this.communicator.loadResourceUsage(x.id, this.start, this.end))
          .filter(e => e.quantity !== 0).sort((a, b) => a.resourceName.localeCompare(b.resourceName)));
      } else {
        tmpResourceList.push(...(await this.communicator.loadResourceUsage(x.id, this.start, this.end))
          .sort((a, b) => a.resourceName.localeCompare(b.resourceName)));
      }
    }
    this.resources = tmpResourceList;
    this.loading = false;
  }
}
