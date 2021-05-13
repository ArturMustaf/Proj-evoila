import {TestBed} from '@angular/core/testing';

import {SearchGridloaderService} from './search-gridloader.service';

describe('SearchGridloaderService', () => {
  let service: SearchGridloaderService;
  const exampleObj = [{
    id: 260,
    name: 'Emogene',
    surname: 'Trevaskiss',
    contactInformation: {
      id: 260,
      address: {
        id: 260,
        address: '8307 Tomscot Circle',
        plz: '80686',
        city: 'München',
        country: {id: 260, countryName: 'Germany', shortDescription: 'Ge'},
        addressType: 'CONTACT_ADDRESS'
      },
      type: 'EMERGENCY_CONTACT',
      email: 'aanderschqe@google.ca',
      numbers: [{number: '0000'}]
    },
    permissions: []
  }, {
    id: 1,
    name: 'Maxy',
    surname: 'Sweetlove',
    contactInformation: {
      id: 1,
      address: {
        id: 1,
        address: '071 Boyd Junction',
        plz: '55124',
        city: 'München',
        country: {id: 1, countryName: 'Germany', shortDescription: 'Ge'},
        addressType: 'BILLING_ADDRESS'
      },
      type: 'MAIN_CONTACT',
      email: 'gsmithiesa0@google.ca',
      numbers: []
    },
    permissions: []
  }];
  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SearchGridloaderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('Customergrid should return surname', () => {
    const searchable = service.loadSearchGrid('customers');
    const surname = service.getField(exampleObj[0], searchable.fieldKeys[0]);
    expect(surname === 'Trevaskiss').toBeTruthy();
    for (const key of searchable.fieldKeys) {
      const field = service.getField(exampleObj[0], key);
      expect(field).not.toBeUndefined();
    }
  });
});
