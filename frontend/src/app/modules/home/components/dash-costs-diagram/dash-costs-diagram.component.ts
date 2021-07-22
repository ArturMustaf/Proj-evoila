import {Component, Input, AfterViewInit} from '@angular/core';

@Component({
  selector: 'app-dash-costs-diagram',
  templateUrl: './dash-costs-diagram.component.html',
  styleUrls: ['./dash-costs-diagram.component.scss']
})

/*
 * docs for the advanced pie chart. ngx-charts are also being used by VMWare vcloud
 * https://swimlane.gitbook.io/ngx-charts/examples/pie-charts/advanced-pie-chart
 */

export class DashCostsDiagramComponent implements AfterViewInit {
  @Input() data: { name: string, value: number }[];

  view: any[] = [700, 300];
  // options

  showLegend: boolean = true;
  showLabels: boolean = true;
  colorScheme = { domain : ['#40708f', '#688ba5', '#8ea6bb', '#b3c3d1', '#d9e1e8', '#ffffff']};

  /*
   * Setting "marginLeft = 15" helps to center the pie chart
   * Setting the overflow property of the pie chart legend is necessary, otherwise the text displayed for the
   *  resource types will be cut out at the top
   */
  ngAfterViewInit(): void {
    let containerOfPieChart = document.querySelector("ngx-charts-advanced-pie-chart > div");
    (containerOfPieChart as HTMLElement).style.marginLeft = "15%";
    let containerOfLegendItems = document.querySelector("ngx-charts-advanced-legend > .advanced-pie-legend > .legend-items-container > .legend-items");
    (containerOfLegendItems as HTMLElement).style.overflow = "visible";
  }

}
