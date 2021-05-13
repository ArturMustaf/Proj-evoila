import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserdataComponent} from './components/userdata/userdata.component';
import {UserpasswordComponent} from './components/userpassword/userpassword.component';

const routes: Routes = [
  {path: '', component: UserdataComponent},
  {path: 'change', component: UserpasswordComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserSettingsRoutingModule {
}
