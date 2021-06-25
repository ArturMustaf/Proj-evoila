import {TestBed} from '@angular/core/testing';

import {RequestServiceService} from './request-service.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';

describe('RequestServiceService', () => {
  let service: RequestServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ]
    });
    service = TestBed.inject(RequestServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
