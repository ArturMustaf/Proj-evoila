import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';


import {ClarityModule} from '@clr/angular';
import {ResourceViewComponent} from './components/resource-view/resource-view.component';
import {CostsComponent} from './components/costs/costs.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {DashComponent} from './components/dash/dash.component';
import {HomeRoutingModule} from './home-routing.module';

import {CostsDiagramComponent} from './components/costs-diagram/costs-diagram.component';
import {DashCostsDiagramComponent} from './components/dash-costs-diagram/dash-costs-diagram.component';

import {TicketsComponent} from './components/tickets/tickets.component';
import {TicketDisplayComponent} from './components/tickets/display/ticketdisplay.component';
import { SingleVDCComponent } from './components/single-vdc/single-vdc.component';
import { ProjectViewComponent } from './components/project-view/project-view.component';


@NgModule({
  declarations: [
    DashComponent,
    ResourceViewComponent,
    CostsComponent,
    CostsDiagramComponent,
    TicketsComponent,
    TicketDisplayComponent,
    SingleVDCComponent,
    ProjectViewComponent

  ],
  imports: [
    CommonModule,
    ClarityModule,
    HomeRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class HomeModule {
}
