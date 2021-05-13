import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NavService} from '../../services/nav/nav.service';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';

@Component({
  selector: 'app-project-dropdown',
  templateUrl: './project-dropdown.component.html',
  styleUrls: ['./project-dropdown.component.scss']
})
/**
 * Here is the project dropdown functionality created. The selection is updated through the NavService
 * Also there is a simple search functionality implemented to search through many projects.
 */
export class ProjectDropdownComponent implements OnInit {
  project: string;
  projects: ProjectDescription[];
  searchString = '';

  constructor(private router: Router, private navService: NavService, private httpService: HttpClient) {
    this.projects = [];

  }

  ngOnInit(): void {
    this.loadProjectInformations();
  }

  async loadProjectInformations(): Promise<ProjectDescription[]> {
    const res = await this.httpService.get<ProjectResponse[]>(environment.backend.address + '/projectInformations').toPromise();
    const result: Array<ProjectDescription> = [];
    for (const response of res) {
      result.push({id: response.id, active: true, description: response.description, name: response.name, link: '404'});
    }
    this.projects = result;
    if (this.projects.length > 0) {
      this.project = this.projects[0].name;
      this.navService.projects = [this.projects[0]];
    }

    return result;
  }

  onClick(project: string): void {
    if (project === 'Auswahl') {
      this.router.navigate(['404']);
    } else {
      this.project = project;
      this.navService.projects = [this.projects.find(elm => elm.name === project)];
    }

  }

  onKeyPress(): void {
    this.projects.forEach((value => {
      value.active = value.name.toLocaleLowerCase().startsWith(this.searchString.toLocaleLowerCase());
    }));
  }


}

export interface ProjectDescription {
  id: number;
  name: string;
  description: string;
  active: boolean;
  link: string;
}

interface ProjectResponse {
  id: number;
  name: string;
  description: string;
}
