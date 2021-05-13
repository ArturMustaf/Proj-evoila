import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AdminPortalRoutingModule} from './admin-portal-routing.module';
import {BasicUsersComponent} from './components/basic-users/basic-users.component';
import {ClarityModule, ClrDatagridModule, ClrSelectModule} from '@clr/angular';


import {GenericFormComponent} from './components/automated/templates/generic-form/generic-form.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {GenericDisplayComponent} from './components/automated/templates/generic-display/generic-display.component';

import {FormDatabaseComponent} from './components/form-database/form-database.component';

import {ChildContainerComponent} from './components/automated/render-templates/child-container/child-container.component';
import {CardComponent} from '../../shared/components/card-component/CardComponent.component';
import {ListContainerComponent} from './components/automated/render-templates/list-container/list-container.component';
import {SearchableGridComponent} from './components/automated/linking-table/searchable-grid/searchable-grid.component';
import {PopupGridComponent} from './components/automated/render-templates/popup-grid/popup-grid.component';
import {EmbeddedGridComponent} from './components/automated/render-templates/embedded-grid/embedded-grid/embedded-grid.component';
import {DatetimepickerComponent} from './components/automated/render-templates/datetimepicker/datetimepicker/datetimepicker.component';


@NgModule({
  declarations: [
    BasicUsersComponent,
    GenericFormComponent,
    GenericDisplayComponent,
    FormDatabaseComponent,
    ChildContainerComponent,
    CardComponent,
    ListContainerComponent,
    SearchableGridComponent,
    PopupGridComponent,
    EmbeddedGridComponent,
    DatetimepickerComponent
  ],
  imports: [
    CommonModule,
    AdminPortalRoutingModule,
    ClrDatagridModule,
    ReactiveFormsModule,
    ClrSelectModule,
    ClarityModule,
    FormsModule

  ]
})
export class AdminPortalModule {
}
