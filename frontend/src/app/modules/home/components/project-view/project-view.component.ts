import {Component, OnInit} from '@angular/core';
import {ProjectModel} from '../../../../shared/models/project-models/project-model';


@Component({
  selector: 'app-project-view',
  templateUrl: './project-view.component.html',
  styleUrls: ['./project-view.component.scss']
})
export class ProjectViewComponent implements OnInit {


  PROJECTS: ProjectModel[]=
    [{id: 1, name: 'evoila', location: 'Mainz', status: 'unhealthy'},
      {id: 1, name: 'evoila1', location: 'Mainz', status: 'healthy'},
      {id: 2, name: 'evoila2', location: 'Wiesbaden', status: 'healthy'},
      {id: 3, name: 'evoila3', location: 'Mainz', status: 'unhealthy'},
      {id: 1, name: 'evoila', location: 'Mainz', status: 'healthy'},
      {id: 1, name: 'evoila1', location: 'Mainz', status: 'healthy'},
      {id: 2, name: 'evoila2', location: 'Wiesbaden', status: 'healthy'},
      {id: 3, name: 'evoila3', location: 'Mainz', status: 'unhealthy'},
      {id: 1, name: 'evoila', location: 'Mainz', status: 'healthy'},
      {id: 1, name: 'evoila1', location: 'Mainz', status: 'healthy'},
      {id: 2, name: 'evoila2', location: 'Wiesbaden', status: 'healthy'},
      {id: 3, name: 'evoila3', location: 'Mainz', status: 'unhealthy'},
      {id: 1, name: 'evoila', location: 'Mainz', status: 'healthy'},
      {id: 1, name: 'evoila1', location: 'Mainz', status: 'healthy'},
      {id: 2, name: 'evoila2', location: 'Wiesbaden', status: 'healthy'},
      {id: 3, name: 'evoila3', location: 'Mainz', status: 'unhealthy'},
      {id: 1, name: 'evoila', location: 'Mainz', status: 'healthy'},
      {id: 1, name: 'evoila1', location: 'Mainz', status: 'healthy'},
      {id: 2, name: 'evoila2', location: 'Wiesbaden', status: 'healthy'},
      {id: 3, name: 'evoila3', location: 'Mainz', status: 'unhealthy'},
      {id: 1, name: 'evoila', location: 'Mainz', status: 'healthy'},
      {id: 1, name: 'evoila1', location: 'Mainz', status: 'healthy'},
      {id: 2, name: 'evoila2', location: 'Wiesbaden', status: 'healthy'},
      {id: 3, name: 'evoila3', location: 'Mainz', status: 'unhealthy'},
      {id: 1, name: 'evoila', location: 'Mainz', status: 'healthy'},
      {id: 1, name: 'evoila1', location: 'Mainz', status: 'healthy'},
      {id: 2, name: 'evoila2', location: 'Wiesbaden', status: 'healthy'},
      {id: 2, name: 'evoila2', location: 'Wiesbaden', status: 'healthy'},
      {id: 2, name: 'evoila2', location: 'Wiesbaden', status: 'healthy'},
      {id: 3, name: 'evoila3', location: 'Mainz', status: 'unhealthy'}];
  constructor() {
  }

  ngOnInit(): void {

  }



}
