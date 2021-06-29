import {TestBed} from '@angular/core/testing';

import {UserinfoService} from './userinfo.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {KeycloakAngularModule} from 'keycloak-angular';


//TODO fix error
xdescribe('UserinfoService', () => {
  let service: UserinfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        KeycloakAngularModule
      ]
    });
    service = TestBed.inject(UserinfoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
