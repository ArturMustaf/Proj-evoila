import {Component, Input, AfterViewInit} from '@angular/core';

@Component({
  selector: 'app-dash-costs-diagram',
  templateUrl: './dash-costs-diagram.component.html',
  styleUrls: ['./dash-costs-diagram.component.scss']
})

export class DashCostsDiagramComponent implements AfterViewInit {

  @Input() data;

  view: any[] = [700, 300];

  // options
  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showLegend = true;
  showXAxisLabel = true;
  xAxisLabel = 'month';
  showYAxisLabel = true;
  yAxisLabel = 'costs in cent';

  colorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
  };

  constructor() {
  }

  ngAfterViewInit(): void {
    (document.querySelector("header.legend-title") as HTMLElement).style.display = "none";
  }
}
