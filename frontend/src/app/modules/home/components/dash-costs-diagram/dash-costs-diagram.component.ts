import {Component, Input, AfterViewInit} from '@angular/core';

@Component({
  selector: 'app-dash-costs-diagram',
  templateUrl: './dash-costs-diagram.component.html',
  styleUrls: ['./dash-costs-diagram.component.scss']
})

export class DashCostsDiagramComponent implements AfterViewInit {
  @Input() data: any[];

  view: any[] = [700, 300];
  // options
  gradient: boolean = true;
  showLegend: boolean = true;
  showLabels: boolean = true;
  colorScheme= { domain : ['#40708f', '#688ba5', '#8ea6bb', '#b3c3d1', '#d9e1e8', '#ffffff']};

  ngAfterViewInit(): void {
    let containerOfPieChart = document.querySelector("ngx-charts-advanced-pie-chart > div");
    (containerOfPieChart as HTMLElement).style.marginLeft = "15%";
    let containerOfLegendItems = document.querySelector("ngx-charts-advanced-legend > .advanced-pie-legend > .legend-items-container > .legend-items");
    (containerOfLegendItems as HTMLElement).style.overflow = "visible";
  }

}
