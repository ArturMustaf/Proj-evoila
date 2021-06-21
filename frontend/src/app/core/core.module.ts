import {BrowserModule, HAMMER_GESTURE_CONFIG, HammerModule} from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';

import {AppComponent} from './core.component';
import {ClarityModule} from '@clr/angular';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {EnsureModuleLoadedOnceGuard} from './EnsureModuleLoadedOnceGuard';
import {RedirectGuard} from './guards/RedirectGuard';
import {CoreRoutingModule} from './core-routing.module';
import {KeycloakAngularModule, KeycloakService} from 'keycloak-angular';
import {DatafieldLoader} from '../modules/admin-portal/service/form-loader/DatafieldLoader';

import {FormsModule, ReactiveFormsModule} from '@angular/forms';

// componentimports
import {HeaderComponent} from './header/header.component';
import {NavigationComponent} from './navigation/navigation.component';
import {SiteframeComponent} from './siteframe/siteframe.component';
import {EmptypageComponent} from '../shared/components/emptypage/emptypage.component';
import {HttpClientModule} from '@angular/common/http';
import {initializer} from './authentification/util/helper';
import {ProjectDropdownComponent} from './navigation/project-dropdown/project-dropdown.component';
import {LY_THEME, LY_THEME_NAME, LyHammerGestureConfig, LyTheme2, StyleRenderer} from '@alyle/ui';
import {MinimaLight} from '@alyle/ui/themes/minima';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    NavigationComponent,
    SiteframeComponent,
    EmptypageComponent,
    ProjectDropdownComponent
  ],
  imports: [
    BrowserModule,
    ClarityModule,
    BrowserAnimationsModule,
    KeycloakAngularModule,
    HttpClientModule,
    CoreRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HammerModule,
    DatafieldLoader
  ],
  providers: [{
    provide: APP_INITIALIZER,
    useFactory: initializer,
    deps: [KeycloakService],
    multi: true
  }, RedirectGuard, {provide: HAMMER_GESTURE_CONFIG, useClass: LyHammerGestureConfig}, StyleRenderer,
    LyTheme2, {provide: LY_THEME_NAME, useValue: 'minima-light'}, {provide: LY_THEME, useClass: MinimaLight, multi: true}],
  bootstrap: [AppComponent]
})
/**
 * Core-Module this module is loaded always and should only contain services and elements which should be loaded only once and be available
 * throughout the complete runtime of the application
 */
export class CoreModule extends EnsureModuleLoadedOnceGuard {
}
