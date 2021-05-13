import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {UserSettingsRoutingModule} from './user-settings-routing.module';
import {UserdataComponent} from './components/userdata/userdata.component';
import {UserpasswordComponent} from './components/userpassword/userpassword.component';
import {UsersettingsComponent} from './components/usersettings/usersettings.component';
import {ClarityModule} from '@clr/angular';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    UserdataComponent,
    UserpasswordComponent,
    UsersettingsComponent
  ],
  imports: [
    CommonModule,
    ClarityModule,
    UserSettingsRoutingModule,
    FormsModule,
    ReactiveFormsModule,
  ]
})
export class UserSettingsModule {
}
