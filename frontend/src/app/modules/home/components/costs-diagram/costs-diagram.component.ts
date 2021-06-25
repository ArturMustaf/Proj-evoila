import {Component, Input, ViewChild} from '@angular/core';
import {CostsResponse} from '../../../../core/services/costs-communicator/mock-costs-communicator.service';
import {Chart} from 'chart.js';
import {CostsCommunicatorService} from '../../../../core/services/costs-communicator/costs-communicator.service';
import {NavService} from '../../../../core/services/nav/nav.service';
import {ProjectDescription} from '../../../../core/navigation/project-dropdown/project-dropdown.component';

@Component({
  selector: 'app-costs-diagram',
  templateUrl: './costs-diagram.component.html',
  styleUrls: ['./costs-diagram.component.scss']
})
/**
 * Diagram of the costs. Costs are seperated over they type
 */
export class CostsDiagramComponent {

  @ViewChild('normalchart', {
    static: true
  }) private chartRef: any;
  chart: any;
  loading: boolean;F
  response: CostsResponse[];
  project: CostsResponse;
  resources = {};
  resourceDetails = [];
  totalCost = 0;
  chartLabels = [];
  chartData = [];
  backgroundColors = ['rgba(255, 99, 132, 0.2)', 'rgba(54, 162, 235, 0.2)', 'rgba(255, 206, 86, 0.2)', 'rgba(75, 192, 192, 0.2)', 'rgba(153, 102, 255, 0.2)', 'rgba(255, 159, 64, 0.2)'];
  borderColors = ['rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)', 'rgba(153, 102, 255, 1)', 'rgba(255, 159, 64, 1)'];
  start = '';
  end = '';
  projectId = '';

  // eslint-disable-next-line @typescript-eslint/naming-convention,no-underscore-dangle,id-blacklist,id-match
  _startDate: Date;

  @Input() set startDate(startDate: string) {
    this.start = startDate;
    this.draw();
  }

  // eslint-disable-next-line @typescript-eslint/naming-convention, no-underscore-dangle, id-blacklist, id-match
  _endDate: Date;

  @Input() set endDate(endDate: string) {
    this.end = endDate;
    this.draw();
  }

  projects: ProjectDescription[];

  constructor(private communicator: CostsCommunicatorService, private navService: NavService) {
    this.projects = navService.projects;
    navService.OnProjectChanged.subscribe((x) => {
      this.projects = x;
      this.draw();
    });
    this.draw();
  }

  async draw(): Promise<void> {
    if (!this.loading) {
      this.loading = true;
      this.chartData = [];
      if (this.chart) {
        this.chart.destroy();
      }
      for (const project of this.projects) {
        this.response = await this.communicator.loadForDiagram(this.start, this.end, project.id);
      }
      this.loading = false;
      this.getEverything(this.response);

      this.chart = new Chart(this.chartRef.nativeElement, {
        type: 'doughnut',
        data: {
          labels: this.chartLabels,
          datasets: [{
            label: 'Kosten je Ressource',
            data: this.chartData,
            backgroundColor: this.backgroundColors,
            borderColor: this.borderColors,
            borderWidth: 1
          }]
        }
      });
    }
  }

  getEverything(data: CostsResponse[]): void {
    if (data.length > 0) {
      this.project = data[0];
      this.resources = this.project.resourceTypes;
      this.totalCost = this.project.totalCosts / 100;
      this.chartLabels = Object.keys(this.resources);
      this.resourceDetails = Object.values(this.resources);
      this.chartData = [];
      this.resourceDetails.forEach(x => {
        this.chartData.push(x.price);
      });
    }
  }
}
