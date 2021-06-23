import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DatetimepickerComponent} from './datetimepicker.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {KeycloakAngularModule} from 'keycloak-angular';
import {RouterTestingModule} from '@angular/router/testing';

describe('DatetimepickerComponent', () => {
  let component: DatetimepickerComponent;
  let fixture: ComponentFixture<DatetimepickerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule, KeycloakAngularModule],
      declarations: [DatetimepickerComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DatetimepickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
