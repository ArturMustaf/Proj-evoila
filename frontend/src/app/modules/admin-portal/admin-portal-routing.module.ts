import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {FormDatabaseComponent} from './components/form-database/form-database.component';
import {SearchableGridComponent} from './components/automated/linking-table/searchable-grid/searchable-grid.component';

export const routes: Routes = [
    {
      path: '', redirectTo: 'grid/projects', pathMatch: 'full'
    },
    {path: 'form-database', component: FormDatabaseComponent},
    {path: 'grid/:path', component: SearchableGridComponent},
    {path: 'forms/:path', component: FormDatabaseComponent},
    {path: 'forms/:path/:id', component: FormDatabaseComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminPortalRoutingModule {
}
