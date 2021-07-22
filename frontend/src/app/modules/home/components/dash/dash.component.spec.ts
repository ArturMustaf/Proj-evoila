import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashComponent } from './dash.component';
import {ResourceModel} from '../../../../shared/models/resource-models/resource-model';
import {ResTypeModel} from '../../../../shared/models/resource-models/res-type-model';
import {PricesModel} from '../../../../shared/models/prices-models/prices-model';

xdescribe('DashComponent', () => {
  let component: DashComponent;
  let fixture: ComponentFixture<DashComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashComponent ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
