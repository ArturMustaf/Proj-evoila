import {TestBed} from '@angular/core/testing';

import {KeycloakGuardGuard} from './keycloak-guard.guard';
import {CoreRoutingModule} from '../core-routing.module';
import {KeycloakAngularModule} from 'keycloak-angular';

describe('KeycloakGuardGuard', () => {
  let guard: KeycloakGuardGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        CoreRoutingModule,
        KeycloakAngularModule
      ]
    });
    guard = TestBed.inject(KeycloakGuardGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
