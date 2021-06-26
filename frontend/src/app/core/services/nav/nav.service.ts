import {EventEmitter, Injectable} from '@angular/core';
import {ProjectDescription} from '../../header-navigation/project-dropdown/project-dropdown.component';

@Injectable({
  providedIn: 'root'
})
/**
 * This service emits whenever the project selection is changes
 * Idea was to support multiple selects to allow to see the resources of multiple projects at once
 */
export class NavService {

  public isAdmin: boolean;
  private Projects: ProjectDescription[];
  private projectChangedEvent: EventEmitter<ProjectDescription[]> = new EventEmitter<ProjectDescription[]>();

  constructor() {
  }

  get OnProjectChanged(): EventEmitter<ProjectDescription[]> {
    return this.projectChangedEvent;
  }

  set projects(project: ProjectDescription[]) {
    this.Projects = project;
    this.projectChangedEvent.emit(this.Projects);
  }
  get projects(): ProjectDescription[] {
    return this.Projects;
  }
}
