import {Component, OnInit} from '@angular/core';
import {KeycloakService} from 'keycloak-angular';
import {Router} from '@angular/router';

@Component({
  selector: 'app-usersettings',
  templateUrl: './usersettings.component.html',
  styleUrls: ['./usersettings.component.scss']
})
/**
 * User settings main page => accessed through top right
 */
export class UsersettingsComponent implements OnInit {

  path: string;


  constructor(private keycloakService: KeycloakService, private router: Router) {
    this.path = router.url.split('/').slice(-1)[0];
  }

  ngOnInit(): void {


  }

}
