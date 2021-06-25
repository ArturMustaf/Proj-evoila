import {TestBed} from '@angular/core/testing';

import {AuthServiceService} from './auth-service.service';
import {KeycloakAngularModule} from 'keycloak-angular';

describe('AuthServiceService', () => {
  let service: AuthServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        KeycloakAngularModule
      ]
    });
    service = TestBed.inject(AuthServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
