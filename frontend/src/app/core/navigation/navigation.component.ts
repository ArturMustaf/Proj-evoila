import {Component, OnInit} from '@angular/core';
import {KeycloakService} from 'keycloak-angular';
import {NavigationEnd, Router} from '@angular/router';
import {NavService} from '../services/nav/nav.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
/**
 * NavigationComponent here are the links to different sub-sites
 */
export class NavigationComponent implements OnInit {

  path: string;

  constructor(private keycloakService: KeycloakService, private router: Router, public nav: NavService) {
    this.path = this.router.url;
    this.router.events.subscribe(value => {
      if (value instanceof NavigationEnd) {
        this.path = value.urlAfterRedirects;
      }
    });
  }

  ngOnInit(): void {
  }

}
