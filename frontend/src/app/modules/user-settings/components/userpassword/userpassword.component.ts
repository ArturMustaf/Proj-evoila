import {Component, OnInit} from '@angular/core';
import {environment} from '../../../../../environments/environment';

@Component({
  selector: 'app-userpassword',
  templateUrl: './userpassword.component.html',
  styleUrls: ['./userpassword.component.scss']
})
/**
 * Password reset page in user-settings
 */
export class UserpasswordComponent implements OnInit {

  issuer: string;
  realm: string;
  url: string;


  constructor() {
    const env = environment;
    this.issuer = env.keycloak.issuer;
    this.realm = env.keycloak.realm;
    this.url = this.issuer + 'realms/' + this.realm + '/account/password';
  }

  ngOnInit(): void {
  }

  onClick(): void {
    window.open(this.url);
  }

}
