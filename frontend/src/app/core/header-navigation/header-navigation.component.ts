import {Component, ElementRef, ViewChild} from '@angular/core';
import {KeycloakService} from 'keycloak-angular';
import {NavService} from '../services/nav/nav.service';
import {UserinfoService} from '../services/userinfo/userinfo.service';

@Component({
  selector: 'app-header-navigation',
  templateUrl: './header-navigation.component.html',
  styleUrls: ['./header-navigation.component.scss']
})
/**
 * Top bar of the page => including menu items and embedded the project dropdown
 */

//TODO - consider refactoring
// Variables 'redirectToAdmin' and 'redirectToUser' reflect the actual roles of the logged in user navigating over the page.
// The variable 'isAdmin' of service 'NavService' is set by angular components (!) that are only available for users with role admin.
// This means that the value of 'isAdmin' is only set to true if the user is navigating over the admin view, false for user view.
// My proposal: refactoring the whole logic behind these variables; displaying the appropriate views should only dependent on the
// role of the user by checking KeycloakService and not by checking whether the currently displayed view is the admin view or not.

export class HeaderNavigationComponent {
  @ViewChild('input1', {static: false})
  private input1: ElementRef;
  username = '';
  redirectToAdmin = false;
  redirectToUser = false;

  constructor(private keycloakService: KeycloakService, public nav: NavService, public userinfoService: UserinfoService) {
    this.setUsername();
    this.setRole();
  }

  async setUsername(): Promise<void> {
    const profile = await this.userinfoService.getUserData();
    this.username = profile.name + ' ' + profile.surname;
  }

  async setRole(): Promise<void> {
    const roles = this.keycloakService.getUserRoles();
    this.redirectToAdmin = roles.includes('admin');
    this.redirectToUser = roles.includes('user');
  }

  logout(): void {
    this.keycloakService.logout();
  }
}
