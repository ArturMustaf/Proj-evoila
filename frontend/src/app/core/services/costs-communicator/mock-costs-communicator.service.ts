import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MockCostsCommunicatorService {
  constructor() {

  }

  // loadCosts(start: string, end: string): Promise<CostsModel[]> {
  //   return new Promise<CostsModel[]>(((resolve) => {
  //     const model = [{postName: 'VM20GB', costs: 60}, {postName: 'VM15GB', costs: 40}, {postName: 'VM50GB', costs: 40},
  //       {postName: 'Storage', costs: 60}, {postName: 'Kubernetes', costs: 150}, {postName: 'IPs', costs: 70},
  //       {postName: 'Cloudfoundry', costs: 40}];
  //     resolve(model);
  //   }));
  // }
  //
  // loadCostsInit(): Promise<CostsModel[]> {
  //   return new Promise<CostsModel[]>(((resolve) => {
  //     const model = [{postName: 'VM20GB', costs: 60}, {postName: 'VM15GB', costs: 40}, {postName: 'VM50GB', costs: 40},
  //       {postName: 'Storage', costs: 60}, {postName: 'Kubernetes', costs: 150}, {postName: 'IPs', costs: 70},
  //       {postName: 'Cloudfoundry', costs: 40}];
  //     resolve(model);
  //   }));
  // }
  //
  // loadForDiagramInit(): Promise<CostsResponse[]> {
  //   return new Promise<CostsResponse[]>(((resolve => {
  //     const model = [{
  //       projectName: 'Projekt1',
  //       totalCost: 460,
  //       resourceTypes: {
  //         VM: {type: 'VM', price: 140},
  //         Storage: {type: 'Storage', price: 60},
  //         Kubernetes: {type: 'Kubernetes', price: 150},
  //         IPs: {type: 'IPs', price: 70},
  //         Cloudfoundry: {type: 'Cloudfoundry', price: 40}
  //       }
  //     }];
  //     resolve(model);
  //   })));
  // }
  //
  // loadForDiagram(start: string, end: string): Promise<CostsResponse[]> {
  //   return new Promise<CostsResponse[]>(((resolve => {
  //     const model = [{
  //       projectName: 'Projekt1',
  //       totalCost: 460,
  //       resourceTypes: {
  //         VM: {type: 'VM', price: 140},
  //         Storage: {type: 'Storage', price: 60},
  //         Kubernetes: {type: 'Kubernetes', price: 150},
  //         IPs: {type: 'IPs', price: 70},
  //         Cloudfoundry: {type: 'Cloudfoundry', price: 40}
  //       }
  //     }];
  //     resolve(model);
  //   })));
  // }

}

export interface CostsResponse {
  projectName: string;
  totalCosts: number;
  resourceTypes: { [key: string]: { type: string, price: number } };
}
