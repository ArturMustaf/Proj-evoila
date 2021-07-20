import {Injectable} from '@angular/core';
import {KeycloakService} from 'keycloak-angular';
import {KeycloakProfile} from 'keycloak-js';

@Injectable({
  providedIn: 'root'
})
/**
 * AuthenticationService this services stores user information from keycloak
 */
export class AuthServiceService {
  private profile: KeycloakProfile;

  private readonly loading: Promise<void>;

  constructor(private keycloakService: KeycloakService) {
    this.loading = this.loadUserProfile();
  }

  async loadUserProfile(): Promise<void> {
    this.profile = await this.keycloakService.loadUserProfile();
  }

  async getProfile(): Promise<KeycloakProfile> {
    await this.loading;

    return this.profile;
  }
}
