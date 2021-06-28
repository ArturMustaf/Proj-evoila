import {ComponentFixture, TestBed} from '@angular/core/testing';

import {FormDatabaseComponent} from './form-database.component';
import {CoreRoutingModule} from '../../../../core/core-routing.module';
import {HttpClientTestingModule} from '@angular/common/http/testing';

describe('FormDatabaseComponent', () => {
  let component: FormDatabaseComponent;
  let fixture: ComponentFixture<FormDatabaseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        CoreRoutingModule,
        HttpClientTestingModule
      ],
      declarations: [FormDatabaseComponent]
    })
        .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormDatabaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
