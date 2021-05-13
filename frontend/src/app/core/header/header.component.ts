import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {KeycloakService} from 'keycloak-angular';
import {Router} from '@angular/router';
import {AuthServiceService} from '../services/auth-service/auth-service.service';
import {NavService} from '../services/nav/nav.service';
import {UserinfoService} from '../services/userinfo/userinfo.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
/**
 * Top bar of the page => including menu items and embedded the project dropdown
 */
export class HeaderComponent implements OnInit {
  @ViewChild('input1', {static: false})
  private input1: ElementRef;
  headername = 'evoila Service Desk';
  username = '';
  redirectToAdmin = false;
  redirectToUser = false;


  constructor(private keycloakService: KeycloakService, private router: Router, private authService: AuthServiceService,
              public nav: NavService, public userinfoService: UserinfoService) {
    console.log('Initialized AppComponent');
    this.setUsername();
    this.setRole();
  }

  async setUsername(): Promise<void> {
    const profile = await this.userinfoService.getUserData();
    this.username = profile.name + ' ' + profile.surname;
  }

  async setRole(): Promise<void> {
    const roles = await this.keycloakService.getUserRoles();
    this.redirectToAdmin = roles.includes('admin');
    this.redirectToUser = roles.includes('user');
  }

  ngOnInit(): void {
  }

  logout(): void {
    this.keycloakService.logout();
  }

  settings(): void {
    this.router.navigate(['/settings']);
  }

  toAdmin(): void {
    this.router.navigate(['/admin']);
  }

  toStart(): void {
    if (this.redirectToAdmin) {
      this.router.navigate(['/admin']);
    } else {
      this.router.navigate(['']);
    }
  }

  toDash(): void {
    this.router.navigate(['']);
  }

}
