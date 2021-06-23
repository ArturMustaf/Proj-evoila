import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashComponent} from './components/dash/dash.component';
import {ResourceViewComponent} from './components/resource-view/resource-view.component';

import {CostsComponent} from './components/costs/costs.component';
import {TicketsComponent} from './components/tickets/tickets.component';


export const routes: Routes = [
  {path: '', component: DashComponent},
  {path: 'resources', component: ResourceViewComponent},
  {path: 'costs', component: CostsComponent},
  {path: 'tickets', component: TicketsComponent}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule {
}
