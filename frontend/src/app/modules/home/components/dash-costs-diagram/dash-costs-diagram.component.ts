import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-dash-costs-diagram',
  templateUrl: './dash-costs-diagram.component.html',
  styleUrls: ['./dash-costs-diagram.component.css']
})
export class DashCostsDiagramComponent implements OnInit {

  @Input() data;

  single: any[];
  multi: any[];

  view: any[] = [700, 400];

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
    Object.assign(this, this.data);
  }

  ngOnInit(): void {
  }

}
