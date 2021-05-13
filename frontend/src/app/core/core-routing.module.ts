import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {RedirectGuard} from './guards/RedirectGuard';
import {EmptypageComponent} from '../shared/components/emptypage/emptypage.component';

const routes: Routes = [
  {
    path: 'auth', canActivate: [RedirectGuard], component: RedirectGuard, data: {
      externalUrl: 'https://cloak.projectmbe.de/auth/'
    }
  },
  {path: '', loadChildren: () => import('../modules/home/home.module').then(m => m.HomeModule)},
  {path: 'settings', loadChildren: () => import('../modules/user-settings/user-settings.module').then(m => m.UserSettingsModule)},
  {path: 'admin', loadChildren: () => import('../modules/admin-portal/admin-portal.module').then(m => m.AdminPortalModule)},
  {path: '404', component: EmptypageComponent}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
/**
 * This routing file redirects to diffrent sub-modules these are lazy loaded
 */
export class CoreRoutingModule {
}
